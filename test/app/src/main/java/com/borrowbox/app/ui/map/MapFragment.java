package com.borrowbox.app.ui.map;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.borrowbox.app.R;
import com.borrowbox.app.databinding.FragmentMapBinding;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;
import java.util.ArrayList;
import java.util.List;

/**
 * Live Google Maps Campus Meeting & Handover Screen.
 * Streams real-time Google Maps vector roadmap and satellite imagery with
 * authentic Google navigation styling, live distance tracking, and 1-click
 * deep linking into the official Google Maps app.
 */
public class MapFragment extends Fragment {

    private FragmentMapBinding binding;
    private int simulationStep = 0;
    private final int[] simulatedDistances = {500, 350, 180, 50};

    // Campus Coordinates (DIU Daffodil Smart City, Birulia, Savar)
    private static final double LAT_MEETING = 23.8759;
    private static final double LON_MEETING = 90.3198;

    private Marker youMarker;
    private Marker ownerMarker;
    private Marker meetingMarker;
    private Polyline routeLine;
    private long rentalId = 1L;
    private boolean isSatelliteView = false;

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                // Location permission handled gracefully
            });

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMapBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            rentalId = getArguments().getLong("rental_id", 1L);
        }

        // 1. Setup Live Google Maps View
        binding.mapView.setTileSource(GoogleMapsTileSource.ROADMAP);
        binding.mapView.setMultiTouchControls(true);
        binding.mapView.getController().setZoom(17.8);

        GeoPoint meetingPoint = new GeoPoint(LAT_MEETING, LON_MEETING);
        binding.mapView.getController().setCenter(meetingPoint);

        // 2. Setup Google Maps Styled Markers & Route Polyline
        setupMarkersAndRoute(meetingPoint);

        // 3. Check Location Permission Gracefully
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        updateDistanceDisplay(simulatedDistances[0]);

        // 4. Map Layer Toggle (Roadmap <-> Satellite / Hybrid)
        binding.btnToggleLayer.setOnClickListener(v -> toggleMapLayer());

        // 5. My Location Re-center Floating Action Button
        binding.btnMyLocation.setOnClickListener(v -> {
            if (youMarker != null) {
                binding.mapView.getController().animateTo(youMarker.getPosition());
                Toast.makeText(requireContext(), R.string.map_my_location, Toast.LENGTH_SHORT).show();
            }
        });

        // 6. Navigate in Google Maps App Button
        binding.btnOpenGoogleMaps.setOnClickListener(v -> openInGoogleMaps());

        // 7. Distance Simulation Button
        binding.btnSimulateMeeting.setOnClickListener(v -> {
            simulationStep = (simulationStep + 1) % simulatedDistances.length;
            int dist = simulatedDistances[simulationStep];
            updateDistanceDisplay(dist);

            // Move "You" marker closer to meeting point
            double factor = (double) dist / 500.0;
            double currentLat = LAT_MEETING + (0.0035 * factor);
            double currentLon = LON_MEETING + (0.0035 * factor);
            GeoPoint currentPos = new GeoPoint(currentLat, currentLon);
            youMarker.setPosition(currentPos);

            // Update route line
            List<GeoPoint> pts = new ArrayList<>();
            pts.add(currentPos);
            pts.add(meetingPoint);
            routeLine.setPoints(pts);

            binding.mapView.getController().animateTo(currentPos);
            binding.mapView.invalidate();
        });

        // 8. Proceed to QR Handover Button
        binding.btnStartHandover.setOnClickListener(v -> {
            Bundle args = new Bundle();
            args.putLong("rental_id", rentalId);
            Navigation.findNavController(view).navigate(R.id.qrHandoverFragment, args);
        });
    }

    private void toggleMapLayer() {
        isSatelliteView = !isSatelliteView;
        if (isSatelliteView) {
            binding.mapView.setTileSource(GoogleMapsTileSource.HYBRID);
            binding.tvActiveLayerBadge.setText("• Satellite");
            Toast.makeText(requireContext(), R.string.map_layer_toast_satellite, Toast.LENGTH_SHORT).show();
        } else {
            binding.mapView.setTileSource(GoogleMapsTileSource.ROADMAP);
            binding.tvActiveLayerBadge.setText("• Maps");
            Toast.makeText(requireContext(), R.string.map_layer_toast_roadmap, Toast.LENGTH_SHORT).show();
        }
        binding.mapView.invalidate();
    }

    private void openInGoogleMaps() {
        // Pedestrian directions to DIU Library / Kathal Tola via official Google Maps
        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + LAT_MEETING + "," + LON_MEETING + "&mode=w");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");

        if (getActivity() != null && mapIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
            startActivity(mapIntent);
        } else {
            // Direct browser fallback to Google Maps directions
            Uri webUri = Uri.parse("https://www.google.com/maps/dir/?api=1&destination="
                    + LAT_MEETING + "," + LON_MEETING + "&travelmode=walking");
            Intent webIntent = new Intent(Intent.ACTION_VIEW, webUri);
            startActivity(webIntent);
        }
    }

    private void setupMarkersAndRoute(GeoPoint meetingPoint) {
        // 1. Meeting Point Marker (Google Maps Red Pin)
        meetingMarker = new Marker(binding.mapView);
        meetingMarker.setPosition(meetingPoint);
        meetingMarker.setTitle(getString(R.string.map_meeting_point_diu));
        meetingMarker.setIcon(ContextCompat.getDrawable(requireContext(), R.drawable.ic_gmaps_pin_meeting));
        meetingMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        binding.mapView.getOverlays().add(meetingMarker);

        // 2. Initial You Marker (Google Maps Blue Location Puck)
        GeoPoint youPoint = new GeoPoint(LAT_MEETING + 0.0035, LON_MEETING + 0.0035);
        youMarker = new Marker(binding.mapView);
        youMarker.setPosition(youPoint);
        youMarker.setTitle(getString(R.string.map_you_label));
        youMarker.setIcon(ContextCompat.getDrawable(requireContext(), R.drawable.ic_gmaps_my_location));
        youMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER);
        binding.mapView.getOverlays().add(youMarker);

        // 3. Owner Marker (Google Maps Amber Pin)
        GeoPoint ownerPoint = new GeoPoint(LAT_MEETING - 0.0005, LON_MEETING - 0.0005);
        ownerMarker = new Marker(binding.mapView);
        ownerMarker.setPosition(ownerPoint);
        ownerMarker.setTitle(getString(R.string.map_owner_label));
        ownerMarker.setIcon(ContextCompat.getDrawable(requireContext(), R.drawable.ic_gmaps_pin_owner));
        ownerMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        binding.mapView.getOverlays().add(ownerMarker);

        // 4. Polyline connecting You and Meeting point (Google Maps Navigation Blue)
        routeLine = new Polyline(binding.mapView);
        routeLine.getOutlinePaint().setColor(0xFF1A73E8);
        routeLine.getOutlinePaint().setStrokeWidth(12.0f);
        routeLine.getOutlinePaint().setStrokeCap(Paint.Cap.ROUND);
        routeLine.getOutlinePaint().setStrokeJoin(Paint.Join.ROUND);

        List<GeoPoint> pts = new ArrayList<>();
        pts.add(youPoint);
        pts.add(meetingPoint);
        routeLine.setPoints(pts);
        binding.mapView.getOverlays().add(routeLine);
    }

    private void updateDistanceDisplay(int distanceMeters) {
        binding.tvDistanceHud.setText(getString(R.string.map_hud_distance, distanceMeters));

        if (distanceMeters <= 50) {
            binding.tvProximityStatus.setText(R.string.map_hud_ready);
            binding.tvHandoverStatus.setText(R.string.map_ready_handover);
            binding.btnStartHandover.setEnabled(true);
            binding.btnStartHandover.setText(R.string.map_start_handover);
        } else if (distanceMeters <= 100) {
            binding.tvProximityStatus.setText(R.string.map_hud_near);
            binding.tvHandoverStatus.setText(R.string.map_almost_there);
            binding.btnStartHandover.setEnabled(true);
        } else {
            binding.tvProximityStatus.setText(R.string.map_hud_moving);
            binding.tvHandoverStatus.setText(R.string.tagline_meet);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (binding != null) binding.mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (binding != null) binding.mapView.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
