package com.suncorp.image.autoadjust;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.suncorp.image.autoadjust.exception.AutoAdjustException;
import com.suncorp.image.autoadjust.gray.GrayProcessor;
import com.suncorp.image.autoadjust.hough.HoughTransform;
import com.suncorp.image.autoadjust.rotate.ImageRotator;
import com.suncorp.image.autoadjust.util.CostUtils;

public class ImageAutoAdjust {

    private String srcImagePath;
    private File srcImageFile;
    private BufferedImage srcBufferedImage;
    private String targetImagePath;
    private CostUtils costUtils = new CostUtils();

    public ImageAutoAdjust(String srcImagePath, String targetImagePath) {
        if (srcImagePath == null || srcImagePath.trim().length() == 0) {
            throw new AutoAdjustException("srcImagePath must have length!");
        }

        srcImageFile = new File(srcImagePath);

        try {
            srcBufferedImage = ImageIO.read(srcImageFile);
        } catch (IOException e) {

            throw new AutoAdjustException("Read image " + srcImagePath
                    + " error, " + e.getMessage(), e);
        }

        setSrcImagePath(srcImagePath);

        if (targetImagePath == null || targetImagePath.trim().length() == 0) {
            targetImagePath = getTempFileName("adjusted", srcImageFile);
        }

        setTargetImagePath(targetImagePath);
    }

    private static String getTempFileName(String temp, File file) {
        String name = file.getName();
        String path = file.getParentFile().getParentFile().getPath();
        System.out.println("path: " + path);
        int lastIndexOf = name.lastIndexOf(".");
        String targetImagePath = file.getName();
        if (lastIndexOf > -1) {
            name = name.substring(0, lastIndexOf) + "_" + temp
                    + name.substring(lastIndexOf);
            targetImagePath = path + File.separator + "adjusted"
                    + File.separator + name;
        }
        return targetImagePath;
    }

    public void process() {
        if (srcBufferedImage == null) {
            throw new AutoAdjustException("source image not set!");
        }
        costUtils.reset();

        BufferedImage image = GrayProcessor.transformToBin(srcBufferedImage);
        costUtils.point("GrayProcessor");

        HoughTransform houghTransform = new HoughTransform();
        int angle = houghTransform.getAngle(image);
        System.err.println("angle = " + angle);
        costUtils.point("HoughTransform");

        BufferedImage desRotate = ImageRotator.rotate(srcBufferedImage, angle);
        costUtils.point("Rotate");
        
//        BufferedImage des = CaptureRectangle.capture(desRotate);
//        costUtils.point("CaptureRectangle");

        try {
            ImageIO.write(desRotate, "jpg", new File(targetImagePath));
            costUtils.point("Write");
        } catch (IOException e) {
            throw new AutoAdjustException("process image " + srcImagePath
                    + " error.", e);
        }
        costUtils.totalCost();
    }

    public ImageAutoAdjust(String srcImagePath) {
        this(srcImagePath, null);
    }

    public ImageAutoAdjust() {

    }

    public String getSrcImagePath() {
        return srcImagePath;
    }

    public void setSrcImagePath(String srcImagePath) {
        this.srcImagePath = srcImagePath;
    }

    public String getTargetImagePath() {
        return targetImagePath;
    }

    public void setTargetImagePath(String targetImagePath) {
        this.targetImagePath = targetImagePath;
    }

}
