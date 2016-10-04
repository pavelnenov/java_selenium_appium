package com.nenov.appiumframework.imageprocessor;

public enum AreaColor {

    BLUE(34, 155, 220),
    ORANGE(239, 154, 66),
    WHITE(255, 255, 255),
    GREEN(140, 193, 82),
    TURQUOISE(0, 255, 255),
    BORDER_GREY(212, 214, 214),
    UNDEFINED(-1, -1, -1);

    int r;
    int g;
    int b;

    AreaColor(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public int getRed() {
        return r;
    }

    public void setRed(int value) {
        this.r = value;
    }

    public int getGreen() {
        return g;
    }

    public void setGreen(int value) {
        this.g = value;
    }

    public int getBlue() {
        return b;
    }

    public void setBlue(int value) {
        this.b = value;
    }
}
