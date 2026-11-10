package com.borrowbox.app.ui.map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.borrowbox.app.R;
import com.borrowbox.app.databinding.FragmentMapBinding;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;
import java.util.ArrayList;
import java.util.List;

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

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                // If granted or denied, map continues working gracefully
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

        // Setup osmdroid map view
        binding.mapView.setTileSource(TileSourceFactory.MAPNIK);
        binding.mapView.setMultiTouchControls(true);
        binding.mapView.getController().setZoom(17.5);

        GeoPoint meetingPoint = new GeoPoint(LAT_MEETING, LON_MEETING);
        binding.mapView.getController().setCenter(meetingPoint);

        setupMarkersAndRoute(meetingPoint);

        // Check location permission gracefully
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        updateDistanceDisplay(simulatedDistances[0]);

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

        binding.btnStartHandover.setOnClickListener(v -> {
            Bundle args = new Bundle();
            args.putLong("rental_id", rentalId);
            Navigation.findNavController(view).navigate(R.id.qrHandoverFragment, args);
        });
    }

    private void setupMarkersAndRoute(GeoPoint meetingPoint) {
        // Meeting Point Marker
        meetingMarker = new Marker(binding.mapView);
        meetingMarker.setPosition(meetingPoint);
        meetingMarker.setTitle("Meeting Point: DIU Library");
        meetingMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        binding.mapView.getOverlays().add(meetingMarker);

        // Initial You Marker (500m away)
        GeoPoint youPoint = new GeoPoint(LAT_MEETING + 0.0035, LON_MEETING + 0.0035);
        youMarker = new Marker(binding.mapView);
        youMarker.setPosition(youPoint);
        youMarker.setTitle("You (Borrower)");
        youMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        binding.mapView.getOverlays().add(youMarker);

        // Owner Marker
        GeoPoint ownerPoint = new GeoPoint(LAT_MEETING - 0.0005, LON_MEETING - 0.0005);
        ownerMarker = new Marker(binding.mapView);
        ownerMarker.setPosition(ownerPoint);
        ownerMarker.setTitle("Owner: Sabbir (Kathal Tola)");
        ownerMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        binding.mapView.getOverlays().add(ownerMarker);

        // Polyline connecting You and Meeting point
        routeLine = new Polyline(binding.mapView);
        routeLine.getOutlinePaint().setColor(ContextCompat.getColor(requireContext(), R.color.brand_primary));
        routeLine.getOutlinePaint().setStrokeWidth(8.0f);
        List<GeoPoint> pts = new ArrayList<>();
        pts.add(youPoint);
        pts.add(meetingPoint);
        routeLine.setPoints(pts);
        binding.mapView.getOverlays().add(routeLine);
    }

    private void updateDistanceDisplay(int distanceMeters) {
        binding.tvDistanceHud.setText(distanceMeters + " m away");

        if (distanceMeters <= 50) {
            binding.tvProximityStatus.setText("• Ready for Handover!");
            binding.tvHandoverStatus.setText(R.string.map_ready_handover);
            binding.btnStartHandover.setEnabled(true);
            binding.btnStartHandover.setText(R.string.map_start_handover);
        } else if (distanceMeters <= 100) {
            binding.tvProximityStatus.setText("• You're almost there!");
            binding.tvHandoverStatus.setText(R.string.map_almost_there);
            binding.btnStartHandover.setEnabled(true);
        } else {
            binding.tvProximityStatus.setText("• Moving towards Meeting Point");
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
