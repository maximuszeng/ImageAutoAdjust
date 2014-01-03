package com.suncorp.image.autoadjust.rectangle;

import java.awt.image.BufferedImage;

/**
 * Capture The Rectangle
 * 
 * @author HeraySoft Inc.
 */
public class CaptureRectangle {

    int minX = -1;
    int maxX = -1;
    int minY = -1;
    int maxY = -1;

    static int getMaxX(BufferedImage orig) {
        int checkRange = (int) (orig.getHeight() * 0.8 / 2);
        int fixedY = orig.getHeight() / 2;
        for (int x = orig.getWidth() - 1; x >= 0; x--) {

            int rgb = orig.getRGB(x, fixedY);
            if (rgb < -100000) {

                int count = 0;
                // Double check: in the same x, the RGB in the vertical range
                // (checkRange * 2 pixel),
                // should be in [-10592673, -197379]
                for (int y = fixedY - checkRange; y <= fixedY + checkRange; y++) {
                    rgb = orig.getRGB(x, y);
                    if (rgb < -100000) {
                        count++;
                    }
                }

                if (count >= checkRange * 2 * 0.96) {
                    return x;
                }
            }
        }
        return -1;

    }

    static int getMinX(BufferedImage orig) {
        int checkRange = (int) (orig.getHeight() * 0.8 / 2);
        int fixedY = orig.getHeight() / 2;

        for (int x = 0; x <= orig.getWidth() - 1; x++) {
            int rgb = orig.getRGB(x, fixedY);
            if (rgb < -100000) {
                int count = 0;
                // Double check: in the same x, the RGB in the vertical range
                // (checkRange * 2 pixel),
                // should be in [-10592673, -197379]
                for (int y = fixedY - checkRange; y <= fixedY + checkRange; y++) {
                    rgb = orig.getRGB(x, y);
                    if (rgb < -100000) {
                        count++;
                    }
                }

                if (count >= checkRange * 2 * 0.96) {
                    return x;
                }
            }
        }
        return -1;
    }

    static int getMaxY(BufferedImage orig) {
        int checkRange = (int) (orig.getWidth() * 0.85 / 2);
        int fixedX = orig.getWidth() / 2;
        for (int y = orig.getHeight() - 1; y >= 0; y--) {
            int rgb = orig.getRGB(fixedX, y);
            if (rgb < -100000) {
                int count = 0;
                // Double check: in the same y, the RGB in the horizontal range
                // (checkRange * 2 pixel),
                // should be in [-10592673, -197379]
                for (int x = fixedX - checkRange; x <= fixedX + checkRange; x++) {
                    rgb = orig.getRGB(x, y);
                    if (rgb < -100000)
                        count++;
                }

                if (count >= checkRange * 2 * 0.96) {
                    return y;
                }
            }
        }
        return -1;
    }

    static int getMinY(BufferedImage orig) {

        int checkRange = (int) (orig.getWidth() * 0.85 / 2);
        System.out.println("checkRange = " + checkRange);
        int fixedX = orig.getWidth() / 2;
        for (int y = 80; y <= orig.getHeight() - 1; y++) {
            int rgb = orig.getRGB(fixedX, y);
            // System.out.println("now y is: " + y);
            // if (rgb >= -10592673 && rgb <= -197379) {

            if (rgb < -100000) {
                int count = 0;
                // Double check: in the same y, the RGB in the horizontal range
                // (checkRange * 2 pixel),
                // should be in [-10592673, -197379]
                for (int x = fixedX - checkRange; x <= fixedX + checkRange; x++) {
                    rgb = orig.getRGB(x, y);

                    if (rgb < -100000) {
                        count++;
                        // if (count > 1070)
                        // System.out.println("now count is: " + count);
                    }
                }

                if (count >= checkRange * 2 * 0.96) {
                    return y;
                }
            }
        }
        return -1;
    }

    public static BufferedImage capture(BufferedImage image) {

        int leftTopX = getMinX(image);
        int leftTopY = getMinY(image);
        int rightButtomX = getMaxX(image);
        int rightButtomY = getMaxY(image);

        int subWidth = rightButtomX - leftTopX;
        int subHeight = rightButtomY - leftTopY;
        
        System.out.println("leftTopX: " + leftTopX);
        System.out.println("leftTopY: " + leftTopY);
        System.out.println("rightButtomX: " + rightButtomX);
        System.out.println("rightButtomY: " + rightButtomY);

        BufferedImage subImage = image.getSubimage(leftTopX, leftTopY,
                subWidth, subHeight);

        return subImage;
    }

}
