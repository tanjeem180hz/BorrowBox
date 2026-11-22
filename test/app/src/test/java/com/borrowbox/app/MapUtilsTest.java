package com.borrowbox.app;

import com.borrowbox.app.util.MapUtils;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for MapUtils geo-distance and ETA calculations.
 */
public class MapUtilsTest {

    @Test
    public void testCalculateDistanceMeters() {
        // DIU Meeting point and nearby point
        double lat1 = 23.8759;
        double lon1 = 90.3198;
        double lat2 = 23.8794;
        double lon2 = 90.3233;

        double distance = MapUtils.calculateDistanceMeters(lat1, lon1, lat2, lon2);
        assertTrue(distance > 400 && distance < 600);
    }

    @Test
    public void testCalculateWalkingEtaMinutes() {
        assertEquals(1, MapUtils.calculateWalkingEtaMinutes(50));
        assertEquals(3, MapUtils.calculateWalkingEtaMinutes(250));
        assertEquals(6, MapUtils.calculateWalkingEtaMinutes(500));
    }

    @Test
    public void testFormatDistance() {
        assertEquals("350 m", MapUtils.formatDistance(350));
        assertEquals("1.2 km", MapUtils.formatDistance(1200));
    }
}
