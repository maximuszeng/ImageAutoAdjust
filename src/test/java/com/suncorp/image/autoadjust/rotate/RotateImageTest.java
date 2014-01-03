package com.suncorp.image.autoadjust.rotate;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.suncorp.image.autoadjust.rotate.ImageRotator;

public class RotateImageTest {

    public static void main(String[] args) throws Exception {

        // MyHough myH = new MyHough();

        // BufferedImage src = ImageIO.read(new
        // File("C:/img-test/hough_test.jpg"));
        BufferedImage src = ImageIO.read(new File("C:/img-test/real_cases/input/06.jpg"));

        BufferedImage des = ImageRotator.rotate(src, 8);
        ImageIO.write(des, "jpg", new File("C:/img-test/real_cases/input/06.rotate_output.jpg"));

        System.out.println("Rotate done!!");

    }
}
