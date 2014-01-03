package com.suncorp.image.autoadjust.crop;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

/**
 * Crop Image
 * 
 * @author HeraySoft Inc.
 */
public class ImageCropper {
    private String srcPath;

    private String tgtPath;

    private int x;
    private int y;

    private int width;
    private int height;

    public ImageCropper() {

    }

    public ImageCropper(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Crop the image and save.
     */
    public void cut() throws IOException {

        FileInputStream is = null;
        ImageInputStream iis = null;

        try {

            is = new FileInputStream(srcPath);

            Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName("jpg");
            ImageReader reader = it.next();

            iis = ImageIO.createImageInputStream(is);

            // true: sequential forward search
            reader.setInput(iis, true);

            ImageReadParam param = reader.getDefaultReadParam();

            Rectangle rect = new Rectangle(x, y, width, height);

            param.setSourceRegion(rect);

            BufferedImage bi = reader.read(0, param);

            ImageIO.write(bi, "jpg", new File(tgtPath));

        } finally {
            if (is != null)
                is.close();
            if (iis != null)
                iis.close();
        }
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getSrcPath() {
        return srcPath;
    }

    public void setSrcPath(String srcPath) {
        this.srcPath = srcPath;
    }

    public String getTgtPath() {
        return tgtPath;
    }

    public void setTgtPath(String tgtPath) {
        this.tgtPath = tgtPath;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

}