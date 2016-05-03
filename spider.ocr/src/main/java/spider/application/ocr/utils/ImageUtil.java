package spider.application.ocr.utils;

import org.apache.commons.lang.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Bill on 2016/5/1.
 */
public class ImageUtil {

    public static void convert(String source, String formatName, String result) {
        try {
            File f = new File(source);
            f.canRead();
            BufferedImage src = ImageIO.read(f);
            ImageIO.write(src, formatName, new File(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void printImage(String source) {
        BufferedImage bi = null;//通过imageio将图像载入
        try {
            File srcFile = new File(source);
            bi = ImageIO.read(srcFile);

        int h = bi.getHeight();//获取图像的高
        int w = bi.getWidth();//获取图像的宽
        int rgb = bi.getRGB(0, 0);//获取指定坐标的ARGB的像素值
        int[][] gray = new int[w][h];
        BufferedImage binaryBufferedImage = new BufferedImage(w, h,
                BufferedImage.TYPE_BYTE_BINARY);
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                if(bi.getRGB(x,y) == -16777216) {
                    Color color = new Color(bi.getRGB(x,y));
                    System.out.print("*");
                    binaryBufferedImage.setRGB(x, y, 0);
                }
                else {
                    Color color = new Color(bi.getRGB(x,y));
                    System.out.print(" ");
                    gray[x][y] = -1;
                    binaryBufferedImage.setRGB(x, y, -1);
                }
//                System.out.print(bi.getRGB(x,y));
            }
            System.out.println("");
        }
            ImageIO.write(binaryBufferedImage, "jpg", new File(srcFile.getParent() +"/tmp1", StringUtils.replace(srcFile.getName(),".png",".jpg")));
        System.out.println("--------------------------------------------------------");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
