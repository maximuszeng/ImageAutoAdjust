package com.suncorp.image.autoadjust.compress;

import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;

public class ImageCompressor {

    /**
     * Compress the image to smaller scale.
     * 
     * @param oImage
     * @param maxWidth
     * @param maxHeight
     * @param newImageName
     * @param fileType  e.g. png, gif, jpg etc.
     * @throws IOException
     */
    public void compressImage(File oImage, String fileType, String fileName)
            throws IOException {
        BufferedImage srcImage = ImageIO.read(oImage);
        int srcWidth = srcImage.getWidth(null);
        int srcHeight = srcImage.getHeight(null);

        if (srcWidth <= 764) {
            saveImage(srcImage, fileType, fileName);
            return;
        }

        // if (srcWidth <= maxWidth && srcHeight <= maxHeight) {
        // saveImage(srcImage, fileType, fileName);
        // return;
        // }

        Image scaledImage = srcImage.getScaledInstance(srcWidth, srcHeight,
                Image.SCALE_SMOOTH);

        double ratio = Math.min((double) 764 / srcWidth, (double) 1024
                / srcHeight);
        AffineTransformOp op = new AffineTransformOp(
                AffineTransform.getScaleInstance(ratio, ratio), null);
        scaledImage = op.filter(srcImage, null);
        saveImage((BufferedImage) scaledImage, fileType, fileName);
    }

    // public void saveImage(BufferedImage image, String fileName) throws
    // IOException {
    //
    // ImageIO.write(image, "jpg", new File(fileName));
    //
    // }

    public void saveImage(BufferedImage image, String fileType, String fileName)
            throws IOException {

        // ImageIO.write(image, fileType, new
        // File("C:/img-test/real_cases/output/compressedImage.jpg"));

        Iterator<ImageWriter> i = ImageIO.getImageWritersByFormatName("jpeg");

        // Just get the first JPEG writer available
        ImageWriter jpegWriter = i.next();

        // Set the compression quality to 0.8
        ImageWriteParam param = jpegWriter.getDefaultWriteParam();
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(0.8f);

        // Write the image to a file
        FileImageOutputStream out = new FileImageOutputStream(
                new File(fileName));
        jpegWriter.setOutput(out);
        jpegWriter.write(null, new IIOImage(image, null, null), param);
        jpegWriter.dispose();
        out.close();

    }

}
