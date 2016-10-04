package com.nenov.appiumframework.util;

import com.nenov.appiumframework.appElements.AbstractElement;
import com.nenov.appiumframework.config.ConfigProperties;
import com.nenov.appiumframework.driver.Driver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.nenov.appiumframework.logging.Logger.log;

public class TestUtils {


    private static String screenshotFileFormat = "png";
    private static String currentDatetimeFormat = "yyyy_MM_dd_HH_mm_ss_SSS";

    public static String getCurrentDateTime() {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(currentDatetimeFormat);
        return sdf.format(now);
    }

    public static void writeToFile(String path, String content) {
        File file = new File(path);
        file.getParentFile().mkdirs();
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(content);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static File takeScreenshot() throws IOException {
        return ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.FILE);
    }

    public static File takeElementScreenshot(AbstractElement element, String outputDir) throws IOException {

        log("Take screenshot of element" + element.getElementName());
        //Get entire page screenshot
        File screenshot = takeScreenshot();
        BufferedImage fullImg = ImageIO.read(screenshot);

        //Get the location of element on the page
        Point point = element.getElement().getLocation();

        //Get width and height of the element
        int elementWidth = element.getElement().getSize().getWidth();
        int elementHeight = element.getElement().getSize().getHeight();

        //Crop the entire page screenshot to get only element screenshot
        BufferedImage elementScreenshot = fullImg.getSubimage(point.getX(), point.getY(), elementWidth, elementHeight);
        ImageIO.write(elementScreenshot, screenshotFileFormat, screenshot);

        //create screenshot file name
        String screenShotFileName = createScreenShotFileName(element, outputDir);

        File screenshotFile = new File(screenShotFileName);
        FileUtils.copyFile(screenshot, screenshotFile);

        log("Saved screenshot to file: " + screenshotFile.getAbsolutePath());
        return new File(screenshotFile.getAbsolutePath());
    }

    private static String createScreenShotFileName(AbstractElement element, String outputDir) {
        String screenshotDirname = ConfigProperties.getInstance().getElementsScreenshotsDirname();
        String elementNameFormatted = element.getElementName().replace(" ", "_");
        String timeStamp = getCurrentDateTime();

        String[] args = { outputDir, screenshotDirname, elementNameFormatted, timeStamp,  File.separator, screenshotFileFormat };
        MessageFormat mf = new MessageFormat("{0}{4}{1}{4}{2}_{3}.{5}");
        return mf.format(args);
    }

}
