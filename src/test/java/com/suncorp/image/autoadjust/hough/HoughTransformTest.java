package com.suncorp.image.autoadjust.hough;

import java.io.File;
import java.io.IOException;

import com.suncorp.image.autoadjust.mp.ImageAutoAdjustTask;

public class HoughTransformTest {

	public static void main(String[] args) throws IOException, InterruptedException {
		// test real cases
		String path = "C:/img-test/real_cases/input";
//				"C:/Users/IBM_ADMIN/Documents/maximus/Code&Samples/code/Auto-Cropping/src/test/resource/samples/real_cases";

		ImageAutoAdjustTask task = new ImageAutoAdjustTask();
		
        task.process(new File(path));
		
	}
}
