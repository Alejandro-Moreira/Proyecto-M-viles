package utils;

import java.util.List;

public class PolygonUtils {

    // Clase interna para representar un punto en coordenadas (latitud, longitud)
    public static class LatLng {
        public final double latitude;
        public final double longitude;

        public LatLng(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }
    }
    // Area en grados
    private static double[] toMercator(double latitud, double longitud) {
        double earthRadius = 6378137; // Radio de la Tierra en metros
        double x = earthRadius * Math.toRadians(longitud);
        double y = earthRadius * Math.log(Math.tan(Math.PI / 4 + Math.toRadians(latitud) / 2));
        return new double[]{x, y};
    }
    // Area en metros
    public static double calculatePolygonArea(List<LatLng> points) {
        int n = points.size();
        if (n < 3) {
            throw new IllegalArgumentException("El polígono debe tener al menos 3 puntos.");
        }

        double area = 0.0;

        for (int i = 0; i < n; i++) {
            LatLng p1 = points.get(i);
            LatLng p2 = points.get((i + 1) % n);

            double[] p1Mercator = toMercator(p1.latitude, p1.longitude);
            double[] p2Mercator = toMercator(p2.latitude, p2.longitude);

            area += p1Mercator[0] * p2Mercator[1];
            area -= p2Mercator[0] * p1Mercator[1];
        }

        area = Math.abs(area) / 2.0;

        return area; // El área está en metros cuadrados.
    }
}