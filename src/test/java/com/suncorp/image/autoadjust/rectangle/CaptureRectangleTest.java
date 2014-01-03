package com.suncorp.image.autoadjust.rectangle;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.suncorp.image.autoadjust.rectangle.CaptureRectangle;

public class CaptureRectangleTest {
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// String filename = "C:/img-test/source-normal-real2.jpg";
		// String filename = "C:/img-test/source-normal.jpg";

		String filename = "C:/img-test/real_cases/output/MyHough_Rotate.jpg";

		// load the file using Java's imageIO library
		BufferedImage orig = ImageIO.read(new File(filename));

		System.out.println("The max X is: " + CaptureRectangle.getMaxX(orig));
		System.out.println("The min X is: " + CaptureRectangle.getMinX(orig));
		System.out.println("The max Y is: " + CaptureRectangle.getMaxY(orig));
		System.out.println("The min Y is: " + CaptureRectangle.getMinY(orig));

		BufferedImage dest = CaptureRectangle.capture(orig);
		ImageIO.write(dest, "jpg", new File("C:/img-test/cap_output.jpg"));

		System.out.println("Rectangle Capture done!!");

	}
}
