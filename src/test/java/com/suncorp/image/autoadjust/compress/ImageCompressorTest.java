package com.suncorp.image.autoadjust.compress;

import java.io.File;
import java.io.IOException;

import com.suncorp.image.autoadjust.compress.ImageCompressor;

public class ImageCompressorTest {

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        String outFileName = "C:/img-test/real_cases/output/compressedImage_13.jpg";

        File oImage = new File("C:/img-test/real_cases/input/13.jpg");
        ImageCompressor imageCompressor = new ImageCompressor();
        imageCompressor.compressImage(oImage, "jpg", outFileName);

        System.out.println("Compress done!");

    }

}
