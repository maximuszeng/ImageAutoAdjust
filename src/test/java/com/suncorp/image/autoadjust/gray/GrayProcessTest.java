package com.suncorp.image.autoadjust.gray;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.suncorp.image.autoadjust.gray.GrayProcessor;

public class GrayProcessTest {

	public static void main(String[] args) throws IOException {
		String filename = "C:/img-test/source-unnormal-part.jpg"; // source-normal-real.jpg
																	// hough_source.jpg";

		// load the file using Java's imageIO library
		BufferedImage image = ImageIO.read(new File(filename));
		BufferedImage grayImage = GrayProcessor.transformToBin(image);

		ImageIO.write(grayImage, "jpg", new File("C:/img-test/gray-output.jpg"));
		System.out.println("Gray end!");
	}

}
