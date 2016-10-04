package com.nenov.appiumframework.imageprocessor;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

public class SampleImageArea {

    private final int sampleAreaPixelOffset = 10;
    private final int requiredPixelMatchPercent = 90;
    private ArrayList<Pixel> pixels;

    public SampleImageArea(BufferedImage image, int xCoord, int yCoord) {
        pixels = new ArrayList<Pixel>();
        getSampleAreaPixelColors(image, xCoord, yCoord);
    }

    //TODO check if this is needed
    public boolean equalsByColor(SampleImageArea areaToCompare) {
        return this.getColor() == areaToCompare.getColor();
    }

    //TODO check if this is needed

    /**
     * Compare SampleImageArea objects pixel by pixel.
     * The default allowed deviation is 10%
     *
     * @param areaToCompare
     * @return true if equal, false if not equal
     */
    public boolean equalsByPixel(SampleImageArea areaToCompare) {
        double matchPercent;
        int pixelsMatched = 0;
        for (int i = 0; i < this.getPixels().size(); i++) {
            if (i >= areaToCompare.getPixels().size()) {
                break;
            }
            if (this.getPixels().get(i).equals(this.getPixels().get(i))) {
                pixelsMatched++;
            }
        }
        matchPercent = pixelsMatched / this.getPixels().size();
        return matchPercent * 10 >= requiredPixelMatchPercent;
    }

    public ArrayList<Pixel> getPixels() {
        return pixels;
    }

    /**
     * Get the predominant color of a SampleImageArea
     *
     * @return AreaColor
     */
    public AreaColor getColor() {
        HashMap<AreaColor, Integer> colors = new HashMap<AreaColor, Integer>();
        for (AreaColor color : AreaColor.values()) {
            colors.put(color, 0);
        }

        for (Pixel pixel : getPixels()) {
            if (!colors.containsKey(pixel.getColor())) {
                colors.put(pixel.getColor(), 1);
            }
            colors.put(pixel.getColor(), colors.get(pixel.getColor()) + 1);
        }

        int maxPixelCount = 0;
        AreaColor predominantColor = null;
        for (AreaColor c : colors.keySet()) {
            if (colors.get(c) > maxPixelCount) {
                maxPixelCount = colors.get(c);
                predominantColor = c;
            }
        }
        return predominantColor;
    }

    private void getSampleAreaPixelColors(BufferedImage image, int x, int y) {
        for (int yy = y - sampleAreaPixelOffset; yy < y + sampleAreaPixelOffset; yy++) {
            for (int xx = x - sampleAreaPixelOffset; xx < x + sampleAreaPixelOffset; xx++) {
                Color color = new Color(image.getRGB(xx, yy));
                pixels.add(new Pixel(xx, yy, color));
            }
        }
    }
}
