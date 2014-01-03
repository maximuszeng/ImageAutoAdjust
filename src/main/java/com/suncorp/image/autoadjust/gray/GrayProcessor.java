package com.suncorp.image.autoadjust.gray;

import java.awt.image.BufferedImage;

/**
 * 
 * Gray Processor provided static method to transform buffered image to binary
 * or gray image.
 * 
 * @author HeraySoft Inc.
 * 
 */
public class GrayProcessor {

    public static BufferedImage process(BufferedImage image) {

        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {

                int pixel = image.getRGB(i, j);
                int r = (pixel & 0xff0000) >> 16;
                int g = (pixel & 0xff00) >> 8;
                int b = (pixel & 0xff);

                if (i > 1500 && j > 2000) {
                    System.out.println("r, g, b: " + r + "," + g + "," + b);
                }

                // int rgb = Math.round(116f * r + 116f * g + 16f * b);

                if (r < 64 && b < 64 && g < 64) {
                    r = 255;
                    b = 0;
                    g = 0;
                }
                int gray = 0xff000000 | r << 16 | g << 8 | b;

                image.setRGB(i, j, gray);

                // new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY),null).filter(image,null);
            }
        }

        return image;
    }

    /**
     * Transform the image from original to binary.
     * 
     * @param image
     * @return transformed image
     */
    public static BufferedImage transformToBin(BufferedImage image) {

        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage grayImage = new BufferedImage(width, height,
                BufferedImage.TYPE_BYTE_BINARY);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int rgb = image.getRGB(i, j);
                grayImage.setRGB(i, j, rgb);
            }
        }
        return grayImage;
    }

    /**
     * Transform the image from original to gray.
     * 
     * @param image
     *            buffered image
     * @return transformed image
     */
    public static BufferedImage transformToGray(BufferedImage image) {

        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage grayImage = new BufferedImage(width, height,
                BufferedImage.TYPE_BYTE_GRAY);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int rgb = image.getRGB(i, j);
                grayImage.setRGB(i, j, rgb);
            }
        }
        return grayImage;
    }
}
