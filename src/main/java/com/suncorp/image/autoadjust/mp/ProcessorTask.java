package com.suncorp.image.autoadjust.mp;

import com.suncorp.image.autoadjust.ImageAutoAdjust;

public class ProcessorTask {
	private String imagePath;

	public ProcessorTask(String imagePath) {
		this.imagePath = imagePath;
	}

	public void process() {
		System.out.println("Start process task " + imagePath);
		ImageAutoAdjust imageAutoAdjust = new ImageAutoAdjust(imagePath);
        imageAutoAdjust.process();
		System.out.println("End process task " + imagePath);
	}
}
