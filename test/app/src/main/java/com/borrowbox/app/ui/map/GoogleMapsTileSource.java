package com.borrowbox.app.ui.map;

import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase;
import org.osmdroid.util.MapTileIndex;

/**
 * Live Google Maps Tile Provider.
 * Streams real-time Google Maps vector-rendered tiles (Roadmap, Satellite, Hybrid, Terrain)
 * without requiring billing-restricted cloud API keys.
 */
public class GoogleMapsTileSource extends OnlineTileSourceBase {

    public static final String LAYER_ROADMAP = "m";
    public static final String LAYER_SATELLITE = "s";
    public static final String LAYER_HYBRID = "y";
    public static final String LAYER_TERRAIN = "p";

    public static final GoogleMapsTileSource ROADMAP =
            new GoogleMapsTileSource("GoogleRoadmap", LAYER_ROADMAP);

    public static final GoogleMapsTileSource SATELLITE =
            new GoogleMapsTileSource("GoogleSatellite", LAYER_SATELLITE);

    public static final GoogleMapsTileSource HYBRID =
            new GoogleMapsTileSource("GoogleHybrid", LAYER_HYBRID);

    public static final GoogleMapsTileSource TERRAIN =
            new GoogleMapsTileSource("GoogleTerrain", LAYER_TERRAIN);

    private final String layer;

    public GoogleMapsTileSource(String name, String layer) {
        super(name, 0, 21, 256, ".png", new String[] {
                "https://mt0.google.com/vt/lyrs=",
                "https://mt1.google.com/vt/lyrs=",
                "https://mt2.google.com/vt/lyrs=",
                "https://mt3.google.com/vt/lyrs="
        });
        this.layer = layer;
    }

    @Override
    public String getTileURLString(long pMapTileIndex) {
        int x = MapTileIndex.getX(pMapTileIndex);
        int y = MapTileIndex.getY(pMapTileIndex);
        int z = MapTileIndex.getZoom(pMapTileIndex);
        return getBaseUrl() + layer + "&x=" + x + "&y=" + y + "&z=" + z;
    }
}
