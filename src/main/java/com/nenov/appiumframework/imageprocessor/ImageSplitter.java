package com.nenov.appiumframework.imageprocessor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static com.nenov.appiumframework.imageprocessor.AreaColor.UNDEFINED;

/**
 * This class is responsible for splitting an image in rectangles.
 * Since a trolley's or a buffers area's image is split into rectangles,
 * splitting the image in rectangles will make possible the verification that an area is highlighted and in what color.
 */
public class ImageSplitter {

    private BufferedImage image;
    private int elementsXAxis;
    private int elementsYAxis;

    private int imageHeightPx;
    private int imageWidthPx;

    private int iterStartYAxis;
    private int iterStartXAxis;

    private int iterStepXAxis;
    private int iterStepYAxis;

    //offsets
    private int leftOffset = 0;
    private int rightOffset = 0;
    private int topOffset = 0;
    private int bottomOffset = 0;

    private SampleImageArea[][] sampleImageAreas;

    public ImageSplitter(File imageFile, int elementsXAxis, int elementsYAxis) throws IOException {
        this(ImageIO.read(imageFile), elementsXAxis, elementsYAxis);
    }

    public ImageSplitter(BufferedImage bufferedImage, int elementsRow, int elementsCol) {
        this.image = bufferedImage;
        this.elementsXAxis = elementsRow;
        this.elementsYAxis = elementsCol;
        sampleImageAreas = new SampleImageArea[elementsCol][elementsRow];
        initializeIterStepsAndOffsets();
    }

    private void initializeIterStepsAndOffsets() {
        leftOffset = Integer.MAX_VALUE;
        rightOffset = Integer.MAX_VALUE;
        topOffset = Integer.MAX_VALUE;
        bottomOffset = Integer.MAX_VALUE;
        imageHeightPx = image.getHeight();
        imageWidthPx = image.getWidth();

        Pixel px;
        for (int y = 0; y < imageHeightPx / elementsYAxis; y++) {
            for (int x = 0; x < imageWidthPx / elementsXAxis; x++) {
                //itarate left-to-right, top-to-bottom
                if (new Pixel(x, y, new Color(image.getRGB(x, y))).getColor() != UNDEFINED) {

                    if (x < leftOffset) {
                        leftOffset = x;
                    }
                    if (y < topOffset) {
                        topOffset = y;
                    }
                }

                //iterate right-to-left, bottom-to-top
                if (new Pixel(imageWidthPx - x - 1, imageHeightPx - y - 1,
                        new Color(image.getRGB(imageWidthPx - x - 1, imageHeightPx - y - 1))).getColor() != UNDEFINED) {
                    if (x < rightOffset) {
                        rightOffset = x;
                    }
                    if (y < bottomOffset) {
                        bottomOffset = y;
                    }
                }
            }
        }

        int workingAreaSizeX = imageWidthPx - leftOffset - rightOffset;
        int workingAreaSizeY = imageHeightPx - topOffset - bottomOffset;
        iterStepXAxis = workingAreaSizeX / elementsXAxis; //i.e. element size in pixels
        iterStepYAxis = workingAreaSizeY / elementsYAxis; //i.e. element size in pixels

        iterStartXAxis = (workingAreaSizeX / elementsXAxis / 2) + leftOffset;
        iterStartYAxis = (workingAreaSizeY / elementsYAxis / 2) + topOffset;
    }

    public SampleImageArea[][] getSampleAreas() {
        for (int y = iterStartYAxis, yy = 0; y < imageHeightPx - bottomOffset; y += iterStepYAxis, yy++) {
            for (int x = iterStartXAxis, xx = 0; x < imageWidthPx - rightOffset; x += iterStepXAxis, xx++) {
                sampleImageAreas[yy][xx] = new SampleImageArea(image, x, y);
            }
        }
        return sampleImageAreas;
    }
}