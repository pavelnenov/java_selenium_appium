package com.nenov.appiumframework.imageprocessor;

import java.awt.*;

import static com.nenov.appiumframework.imageprocessor.AreaColor.UNDEFINED;

public class Pixel {

    private int xCoord;
    private int yCoord;
    private Color color;
    private int comparisonTolerance = 15;

    public Pixel(int xCoord, int yCoord, Color color) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.color = color;
    }

    public int getXCoord() {
        return xCoord;
    }

    public int getYCoord() {
        return yCoord;
    }

    public int[] getCoords() {
        return new int[] { getXCoord(), getYCoord() };
    }

    public AreaColor getColor() {
        for (AreaColor color : AreaColor.values()) {
            if (getColorRGB().getRed() == color.getRed() && getColorRGB().getGreen() == color.getGreen()
                    && getColorRGB().getBlue() == color.getBlue())
                return color;
        }
        return UNDEFINED;
    }

    public Color getColorRGB() {
        return color;
    }

    //TODO check if this is needed
    public boolean equals(Pixel pixelToCompare) {
        if (this.getXCoord() != pixelToCompare.getXCoord() && this.getYCoord() != pixelToCompare.getYCoord()) {
            return false;
        }
        return isWithinToleranceValues(this.getColorRGB().getRed(), pixelToCompare.getColorRGB().getRed())
                && isWithinToleranceValues(this.getColorRGB().getGreen(), pixelToCompare.getColorRGB().getGreen())
                && isWithinToleranceValues(this.getColorRGB().getBlue(), pixelToCompare.getColorRGB().getBlue());
    }

    private boolean isWithinToleranceValues(int v1, int v2) {
        return absValue(v1, v1) < comparisonTolerance;
    }

    private int absValue(int v1, int v2) {
        return (int) Math.abs((double) v1 - (double) v2);
    }
}
