package com.suncorp.image.autoadjust.crop;

import com.suncorp.image.autoadjust.crop.ImageCropper;

public class ImageCropperTest {
    public static void main(String[] args) throws Exception {

        String image = "c:/img-test/source-unnormal.jpg";
        ImageCropper o = new ImageCropper(1300, 0, 1700 - 1300, 2336);
        o.setSrcPath(image);
        // o.setSubpath("c:/img-test/source-cropped.jpg");
        o.setTgtPath("c:/img-test/source-unnormal-part.jpg");
        o.cut();
        System.out.println("Image Cropped done!!");

        // String image2 = "C:/img-test/gray-output.jpg";
        // //"c:/img-test/source-normal-real2.jpg";
        // BufferedImage orig = javax.imageio.ImageIO.read(new File(image2));
        // CaptureRectangle cr = new CaptureRectangle();
        // int minX = cr.getMinX(orig);
        // int maxX = cr.getMaxX(orig);
        // int minY = cr.getMinY(orig);
        // int maxY = cr.getMaxY(orig);
        // System.out.println("minX: " + minX);
        // System.out.println("maxX: " + maxX);
        // System.out.println("minY: " + minY);
        // System.out.println("maxY: " + maxY);
        // CropImage o2 = new CropImage(minX, minY, maxX-minX, maxY-minY);
        // o2.setSrcpath(image2);
        // o2.setSubpath("c:/img-test/source-cropped.jpg");
        // o2.cut();

    }
}
