package com.suncorp.image.autoadjust.hough;

import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HoughTransform {

    private int steps = 500;
    private int[][] hough_2d;

    // private float threshold = 0.6f;
    // private float scale;
    // private float offset;

    /**
     * Convert pixels array from 1D to 2D.
     * 
     * @param image  BufferedImage
     * @return 2D integer array
     */
    private int[][] convert1Dto2D(BufferedImage image) {

        int width = image.getWidth();
        int height = image.getHeight();

        int[][] image_2d = new int[height][width];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                image_2d[row][col] = image.getRGB(col, row);
            }
        }
        return image_2d;
    }

    public int getAngle(BufferedImage image) {

        int width = image.getWidth();
        int height = image.getHeight();

        // add compress process here to reduce the expense
        if (width > 764) {
            Image scaledImage = image.getScaledInstance(width, height,
                    Image.SCALE_SMOOTH);
            double ratio = Math.min((double) 764 / width, (double) 1024
                    / height);
            AffineTransformOp op = new AffineTransformOp(
                    AffineTransform.getScaleInstance(ratio, ratio), null);
            scaledImage = op.filter(image, null);
            image = (BufferedImage) scaledImage;
            width = image.getWidth();
            height = image.getHeight();
        }

        System.out.println("image width: " + width);
        System.out.println("image height: " + height);

        // prepare for hough transform
        int centerX = width / 2;
        int centerY = height / 2;
        double hough_interval = Math.PI / (double) steps;

        int max = Math.max(width, height);

        System.out.println("max is: " + max);

        int max_r = (int) (Math.sqrt(2.0D) * max);

        System.out.println("max_r is: " + max_r);

//        int[] hough_1d = new int[2 * steps * max_r];

        // define temp hough 2D array and initialize the hough 2D
        hough_2d = new int[steps][2 * max_r];
        for (int i = 0; i < steps; i++) {
            for (int j = 0; j < 2 * max_r; j++) {
                hough_2d[i][j] = 0;
            }
        }

        // start hough transform now....
        int[][] image_2d = convert1Dto2D((BufferedImage) image);
        for (int img_row = 0; img_row < height; img_row++) {
            for (int img_col = 0; img_col < width; img_col++) {
                
                // transform the number into binary, only capture the low 8 bit
                int p = image_2d[img_row][img_col] & 0xff; // 0xff 0x000000ff
                if (p == 255)
                    continue; // which means background color

                // since we does not know the theta angle and r value,
                // we have to calculate all hough space for each pixel point
                // then we got the max possible theta and r pair.
                // r = x * cos(theta) + y * sin(theta)
                for (int step_no = 0; step_no < steps; step_no++) {

                    // only consider the theta < 45 degree or theta > 135 degree
                    if (step_no > steps / 4 && step_no < steps * 3 / 4) {
                        continue;
                    }

                    int r = (int) ((img_col - centerX)
                            * Math.cos(step_no * hough_interval) + (img_row - centerY)
                            * Math.sin(step_no * hough_interval));
                    r += max_r;

                    // start from zero, not (-max_r)
                    if (r < 0 || (r >= 2 * max_r)) {
                        // make sure r did not out of scope[0, 2*max_r]
                        continue;
                    }
                    hough_2d[step_no][r] += 1;
                }
            }
        }

        // find the max hough value
        int max_hough = 0;
        double max_theta = 0;
        for (int i = 0; i < steps; i++) {
            for (int j = 0; j < 2 * max_r; j++) {
                // hough_1d[(i + j * step)] = hough_2d[i][j]; //non-used right now!
                if (hough_2d[i][j] > max_hough) {
                    max_hough = hough_2d[i][j];
                    max_theta = i * hough_interval;
                }
            }
        }
        System.out.println("Max hough value = " + max_hough);
        System.out.println("Max theta = " + max_theta);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(df.format(new Date()));

        Long angle_l = Math.round(max_theta / Math.PI * 180);

        int angle = angle_l.intValue();

        if (angle_l < 90) {
            angle = -1 * angle;
        } else if (angle_l >= 90) {
            angle = 180 - angle;
        }

        System.out.println("angle_rad = " + max_theta);
        System.out.println("angle_l = " + angle_l);
        System.out.println("angle = " + angle);

        return angle;

    }
}
