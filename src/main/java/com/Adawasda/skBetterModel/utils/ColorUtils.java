package com.Adawasda.skBetterModel.utils;

public final class ColorUtils {

    private ColorUtils() {}

    public static int rgbToInt(int r, int g, int b) {
        r = Math.clamp(r, 0, 255);
        g = Math.clamp(g, 0, 255);
        b = Math.clamp(b, 0, 255);
        return (r << 16) | (g << 8) | b;
    }

    public static int fromSkriptColor(ch.njol.skript.util.Color color) {
        return rgbToInt(color.getRed(), color.getGreen(), color.getBlue());
    }
}
