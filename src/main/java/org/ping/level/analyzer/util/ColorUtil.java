package org.ping.level.analyzer.util;

import java.awt.*;

public class ColorUtil {
    public static String toPseudoColor(double value, double min, double max) {
        double h = ((value - max) / (min - max)) * 120.0;
        Color c = Color.getHSBColor((float) (h / 360.0), 1, 1);
        String hex = String.format("#%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue());
        return hex;
    }
}
