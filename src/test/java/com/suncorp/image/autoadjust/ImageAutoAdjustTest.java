package com.suncorp.image.autoadjust;

import java.io.File;
import java.io.IOException;

import com.suncorp.image.autoadjust.mp.ImageAutoAdjustTask;

public class ImageAutoAdjustTest {

	/**
	 * @param args
	 */
    public static void main(String[] args) throws IOException, InterruptedException {
        // test real cases
        String path = "C:/img-test/real_cases/input";
        ImageAutoAdjustTask task = new ImageAutoAdjustTask();
        task.process(new File(path));
    }

}
