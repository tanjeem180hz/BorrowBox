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
import com.borrowbox.app.databinding.DialogGoogleMapLayersBinding;
import com.borrowbox.app.databinding.FragmentMapBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import org.osmdroid.events.MapListener;
import org.osmdroid.events.ScrollEvent;
import org.osmdroid.events.ZoomEvent;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay;
import java.util.ArrayList;
import java.util.List;

/**
 * Live Google Maps Campus Meeting & Handover Screen.
 * Complete with official Google Maps styling, Zoom In/Out (+/-) buttons,
 * dynamic North-aligned compass, multi-layer selector (Roadmap, Satellite, Terrain),
 * scale bar, search & walking ETA header, Google Maps action chips (Directions, Start, Call, Share),
 * and live distance simulation.
 */
public class MapFragment extends Fragment {

    private FragmentMapBinding binding;
    private int simulationStep = 0;
    private final int[] simulatedDistances = {500, 350, 180, 50};
    private final int[] simulatedWalkingMinutes = {6, 4, 2, 1};

    // Campus Coordinates (DIU Daffodil Smart City, Birulia, Savar)
    private static final double LAT_MEETING = 23.8759;
    private static final double LON_MEETING = 90.3198;

    private Marker youMarker;
    private Marker ownerMarker;
    private Marker meetingMarker;
    private Polyline routeLine;
    private long rentalId = 1L;
    private String currentLayerMode = "roadmap"; // "roadmap", "satellite", "terrain"

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

        // 1. Initialize Google Maps Tile Source
        binding.mapView.setTileSource(GoogleMapsTileSource.ROADMAP);
        binding.mapView.setMultiTouchControls(true);
        binding.mapView.getController().setZoom(17.8);

        GeoPoint meetingPoint = new GeoPoint(LAT_MEETING, LON_MEETING);
        binding.mapView.getController().setCenter(meetingPoint);

        // 2. Add Two-Finger Rotation & Metric Scale Bar Overlays
        try {
            RotationGestureOverlay rotationGestureOverlay = new RotationGestureOverlay(binding.mapView);
            rotationGestureOverlay.setEnabled(true);
            binding.mapView.getOverlays().add(rotationGestureOverlay);

            ScaleBarOverlay scaleBarOverlay = new ScaleBarOverlay(binding.mapView);
            scaleBarOverlay.setCentred(false);
            scaleBarOverlay.setScaleBarOffset(40, 20);
            binding.mapView.getOverlays().add(scaleBarOverlay);
        } catch (Exception ignored) {}

        // 3. Setup Google Maps Styled Markers & Route
        setupMarkersAndRoute(meetingPoint);

        // 4. Track Map Orientation for Dynamic Compass Rotation
        binding.mapView.addMapListener(new MapListener() {
            @Override
            public boolean onScroll(ScrollEvent event) {
                updateCompassRotation();
                return false;
            }

            @Override
            public boolean onZoom(ZoomEvent event) {
                updateCompassRotation();
                return false;
            }
        });

        // 5. Compass Button (Re-orient North)
        binding.cardCompass.setOnClickListener(v -> {
            binding.mapView.setMapOrientation(0.0f);
            binding.ivCompassNeedle.animate().rotation(0f).setDuration(250).start();
            Toast.makeText(requireContext(), R.string.gmaps_toast_north, Toast.LENGTH_SHORT).show();
        });

        // 6. Google Maps Zoom Controls (+ and -)
        binding.btnZoomIn.setOnClickListener(v -> {
            binding.mapView.getController().zoomIn();
        });

        binding.btnZoomOut.setOnClickListener(v -> {
            binding.mapView.getController().zoomOut();
        });

        // 7. Layer Switcher Sheet Dialog
        binding.btnToggleLayer.setOnClickListener(v -> showMapLayerSheet());

        // 8. My Location Re-center Button
        binding.btnMyLocation.setOnClickListener(v -> {
            if (youMarker != null) {
                binding.mapView.getController().animateTo(youMarker.getPosition());
                Toast.makeText(requireContext(), R.string.gmaps_toast_recenter, Toast.LENGTH_SHORT).show();
            }
        });

        // 9. Google Maps Action Chips
        binding.chipDirections.setOnClickListener(v -> openInGoogleMaps());
        binding.chipStart.setOnClickListener(v -> openInGoogleMaps());
        binding.chipCall.setOnClickListener(v -> {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:+8801712345678"));
            try {
                startActivity(callIntent);
            } catch (Exception e) {
                Toast.makeText(requireContext(), "Owner phone: +8801712345678", Toast.LENGTH_SHORT).show();
            }
        });
        binding.chipShare.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "BorrowBox Meeting Location");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Meeting Point: DIU Knowledge Tower / Library (BorrowBox)\nhttps://maps.google.com/?q=" + LAT_MEETING + "," + LON_MEETING);
            startActivity(Intent.createChooser(shareIntent, getString(R.string.gmaps_chip_share)));
        });

        // 10. Distance Simulation Button
        binding.btnSimulateMeeting.setOnClickListener(v -> {
            simulationStep = (simulationStep + 1) % simulatedDistances.length;
            int dist = simulatedDistances[simulationStep];
            int mins = simulatedWalkingMinutes[simulationStep];
            updateDistanceDisplay(dist, mins);

            // Move borrower marker closer to meeting point
            double factor = (double) dist / 500.0;
            double currentLat = LAT_MEETING + (0.0035 * factor);
            double currentLon = LON_MEETING + (0.0035 * factor);
            GeoPoint currentPos = new GeoPoint(currentLat, currentLon);
            youMarker.setPosition(currentPos);

            // Update route polyline
            List<GeoPoint> pts = new ArrayList<>();
            pts.add(currentPos);
            pts.add(meetingPoint);
            routeLine.setPoints(pts);

            binding.mapView.getController().animateTo(currentPos);
            binding.mapView.invalidate();
        });

        // 11. Proceed to QR Handover Button
        binding.btnStartHandover.setOnClickListener(v -> {
            Bundle args = new Bundle();
            args.putLong("rental_id", rentalId);
            Navigation.findNavController(view).navigate(R.id.qrHandoverFragment, args);
        });

        // 12. Check Location Permission Gracefully
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        updateDistanceDisplay(simulatedDistances[0], simulatedWalkingMinutes[0]);
    }

    private void updateCompassRotation() {
        if (binding != null) {
            float orientation = binding.mapView.getMapOrientation();
            binding.ivCompassNeedle.setRotation(-orientation);
        }
    }

    private void showMapLayerSheet() {
        if (getContext() == null) return;
        BottomSheetDialog dialog = new BottomSheetDialog(requireContext());
        DialogGoogleMapLayersBinding sheetBinding = DialogGoogleMapLayersBinding.inflate(getLayoutInflater());
        dialog.setContentView(sheetBinding.getRoot());

        // Highlight current active layer
        updateLayerSheetBorders(sheetBinding);

        sheetBinding.cardLayerDefault.setOnClickListener(v -> {
            currentLayerMode = "roadmap";
            binding.mapView.setTileSource(GoogleMapsTileSource.ROADMAP);
            binding.tvActiveLayerBadge.setText("• Maps");
            Toast.makeText(requireContext(), R.string.map_layer_toast_roadmap, Toast.LENGTH_SHORT).show();
            binding.mapView.invalidate();
            dialog.dismiss();
        });

        sheetBinding.cardLayerSatellite.setOnClickListener(v -> {
            currentLayerMode = "satellite";
            binding.mapView.setTileSource(GoogleMapsTileSource.HYBRID);
            binding.tvActiveLayerBadge.setText("• Satellite");
            Toast.makeText(requireContext(), R.string.map_layer_toast_satellite, Toast.LENGTH_SHORT).show();
            binding.mapView.invalidate();
            dialog.dismiss();
        });

        sheetBinding.cardLayerTerrain.setOnClickListener(v -> {
            currentLayerMode = "terrain";
            binding.mapView.setTileSource(GoogleMapsTileSource.TERRAIN);
            binding.tvActiveLayerBadge.setText("• Terrain");
            Toast.makeText(requireContext(), "Switched to Google Maps Terrain View", Toast.LENGTH_SHORT).show();
            binding.mapView.invalidate();
            dialog.dismiss();
        });

        dialog.show();
    }

    private void updateLayerSheetBorders(DialogGoogleMapLayersBinding sheetBinding) {
        int primaryColor = ContextCompat.getColor(requireContext(), R.color.brand_primary);
        int defaultColor = 0xFFDADCE0;

        sheetBinding.cardLayerDefault.setStrokeColor("roadmap".equals(currentLayerMode) ? primaryColor : defaultColor);
        sheetBinding.cardLayerDefault.setStrokeWidth("roadmap".equals(currentLayerMode) ? 4 : 2);

        sheetBinding.cardLayerSatellite.setStrokeColor("satellite".equals(currentLayerMode) ? primaryColor : defaultColor);
        sheetBinding.cardLayerSatellite.setStrokeWidth("satellite".equals(currentLayerMode) ? 4 : 2);

        sheetBinding.cardLayerTerrain.setStrokeColor("terrain".equals(currentLayerMode) ? primaryColor : defaultColor);
        sheetBinding.cardLayerTerrain.setStrokeWidth("terrain".equals(currentLayerMode) ? 4 : 2);
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
        meetingMarker.setOnMarkerClickListener((marker, mapView) -> {
            mapView.getController().animateTo(marker.getPosition());
            Toast.makeText(requireContext(), R.string.map_meeting_point_diu, Toast.LENGTH_SHORT).show();
            return true;
        });
        binding.mapView.getOverlays().add(meetingMarker);

        // 2. Initial You Marker (Google Maps Blue Location Puck)
        GeoPoint youPoint = new GeoPoint(LAT_MEETING + 0.0035, LON_MEETING + 0.0035);
        youMarker = new Marker(binding.mapView);
        youMarker.setPosition(youPoint);
        youMarker.setTitle(getString(R.string.map_you_label));
        youMarker.setIcon(ContextCompat.getDrawable(requireContext(), R.drawable.ic_gmaps_my_location));
        youMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER);
        youMarker.setOnMarkerClickListener((marker, mapView) -> {
            mapView.getController().animateTo(marker.getPosition());
            Toast.makeText(requireContext(), R.string.map_you_label, Toast.LENGTH_SHORT).show();
            return true;
        });
        binding.mapView.getOverlays().add(youMarker);

        // 3. Owner Marker (Google Maps Amber Pin)
        GeoPoint ownerPoint = new GeoPoint(LAT_MEETING - 0.0005, LON_MEETING - 0.0005);
        ownerMarker = new Marker(binding.mapView);
        ownerMarker.setPosition(ownerPoint);
        ownerMarker.setTitle(getString(R.string.map_owner_label));
        ownerMarker.setIcon(ContextCompat.getDrawable(requireContext(), R.drawable.ic_gmaps_pin_owner));
        ownerMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        ownerMarker.setOnMarkerClickListener((marker, mapView) -> {
            mapView.getController().animateTo(marker.getPosition());
            Toast.makeText(requireContext(), R.string.map_owner_label, Toast.LENGTH_SHORT).show();
            return true;
        });
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

    private void updateDistanceDisplay(int distanceMeters, int minutesWalk) {
        binding.tvWalkingEta.setText(getString(R.string.gmaps_walking_eta, distanceMeters, minutesWalk));

        if (distanceMeters <= 50) {
            binding.tvHandoverStatus.setText(R.string.map_hud_ready);
            binding.layoutStatusBanner.setBackgroundColor(0xFFE6F4EA); // Soft Google green
            binding.tvHandoverStatus.setTextColor(0xFF137333);
            binding.btnStartHandover.setEnabled(true);
            binding.btnStartHandover.setText(R.string.map_start_handover);
        } else if (distanceMeters <= 100) {
            binding.tvHandoverStatus.setText(R.string.map_hud_near);
            binding.layoutStatusBanner.setBackgroundColor(0xFFFEF7E0); // Soft Google yellow
            binding.tvHandoverStatus.setTextColor(0xFFB06000);
            binding.btnStartHandover.setEnabled(true);
        } else {
            binding.tvHandoverStatus.setText(R.string.map_hud_moving);
            binding.layoutStatusBanner.setBackgroundColor(0xFFE8F0FE); // Soft Google blue
            binding.tvHandoverStatus.setTextColor(0xFF1A73E8);
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
