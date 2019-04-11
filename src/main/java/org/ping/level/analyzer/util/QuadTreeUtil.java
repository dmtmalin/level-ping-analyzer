package org.ping.level.analyzer.util;

public class QuadTreeUtil {
    private static double tileSize = 256.0;

    public static String latLngToQuad(double lat, double lng) {
        double depth = 30.0;
        double[] xy = latLngToPoint(lat, lng);
        int xTile = (int) Math.floor(xy[0] * Math.pow(2, depth) / tileSize);
        int yTile = (int) Math.floor(xy[1] * Math.pow(2, depth) / tileSize);
        return encode(xTile, yTile, (int) depth);
    }

    public static double[] latLngToPoint(double lat, double lng) {
        double x = ((lng + 180.0) / 360.0) * tileSize;
        double sinLat = Math.sin(lat * Math.PI / 180.0);
        double y = (0.5 - Math.log((1.0 + sinLat) / (1.0 - sinLat)) / (4.0 * Math.PI)) * tileSize;
        return new double[] {x, y};
    }

    public static String encode(int x, int y, int depth) {
        StringBuilder sb = new StringBuilder();
        for(int i = depth; i > 0; i--) {
            int pow = 1 << (i - 1);
            int cell = 0;
            if((x & pow) != 0) cell++;
            if((y & pow) != 0) cell += 2;
            sb.append(cell);
        }
        return sb.toString();
    }

    public static int[] decode(String quad) {
        String[] arr = quad.split("");
        int[][] keyChain = {{0,0}, {1,0}, {0,1}, {1, 1}};
        double x=0, y=0;
        for (int i = arr.length; i > 0; i--){
            int mask = 1 << i;
            x += keyChain[Integer.parseInt(arr[i-1])][0] / (double)mask;
            y += keyChain[Integer.parseInt(arr[i-1])][1] / (double)mask;
        }
        x *= 1<<arr.length;
        y *= 1<<arr.length;
        return new int[] { (int)x, (int)y, arr.length };
    }

    public static String nextDoor(String quad, int xoff, int yoff) {
        int xyz[] = decode(quad);
        int x = xyz[0] + xoff;
        int y = xyz[1] + yoff;
        return encode(x, y, xyz[2]);
    }

    public static double[] fromPointToLatLng(double x, double y) {
        x = x / tileSize - 0.5;
        y = y / tileSize - 0.5;
        double lat = 90 - 360 * Math.atan(Math.exp(y * 2 * Math.PI)) / Math.PI;
        double lng = 360 * x;
        return new double[] {lat, lng};
    }

    public static double[][] quadToBox(String quad) {
        double[] latLng0 = quadToLatLng(quad);
        String nextQuad = nextDoor(quad, 1, 1);
        double[] latLng1 = quadToLatLng(nextQuad);
        double[][] box = {
                { latLng0[1], latLng0[0] },
                { latLng1[1], latLng0[0] },
                { latLng1[1], latLng1[0] },
                { latLng0[1], latLng1[0] }
        };
        return box;
    }

    public static double[] quadToLatLng(String quad) {
        int[] xyz = decode(quad);
        double x = tileSize * xyz[0] / Math.pow(2, xyz[2]);
        double y = tileSize * xyz[1] / Math.pow(2, xyz[2]);
        return fromPointToLatLng(x, y);
    }
}