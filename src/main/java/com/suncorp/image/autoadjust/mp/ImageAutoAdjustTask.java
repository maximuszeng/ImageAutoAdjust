package com.suncorp.image.autoadjust.mp;

import java.io.File;
import java.io.FilenameFilter;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ImageAutoAdjustTask {
	public static final int FACTOR_SCALE = 2;
	private ExecutorService executorService;

	public static int getMaxThreads() {
		int cpus = Runtime.getRuntime().availableProcessors();
		int maxThreads = cpus * FACTOR_SCALE;
		maxThreads = (maxThreads > 0 ? maxThreads : 1);
		return maxThreads;
	}

	public ImageAutoAdjustTask() {
		int maxThreads = getMaxThreads();
		// param1: core thread pool size
		// param2: maximum thread pool size
		// param3: time to wait before resizing pool
		executorService = new ThreadPoolExecutor(maxThreads, maxThreads, 1, TimeUnit.MINUTES,
				new ArrayBlockingQueue<Runnable>(maxThreads, true), new ThreadPoolExecutor.CallerRunsPolicy());
	}

	public void process(File rootPath) {

		processInternal(rootPath);

		// wait for all of the executor threads to finish
		executorService.shutdown();

		try {
			if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
				// pool didn't terminate after the first try
				executorService.shutdownNow();
			}

			if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
				// pool didn't terminate after the second try
			}
		} catch (InterruptedException ex) {
			executorService.shutdownNow();
			Thread.currentThread().interrupt();
		}

	}

	private void processInternal(File dict) {

		if (dict == null || !dict.isDirectory()) {
			return;
		}

		File[] listFiles = dict.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				if (dir.isDirectory()) {
					return true;
				} else {
					return name.endsWith("jpg");
				}
			}
		});

		for (File item : listFiles) {
			final File itemFinal = item;
			if (itemFinal.isDirectory()) {
				processInternal(item);
			} else {
				executorService.submit(new Runnable() {
					public void run() {
						try {
							new ProcessorTask(itemFinal.getAbsolutePath()).process();
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				});
			}
		}
	}
}
