package spider.application.ocr.util;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

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

    public static String printImage(String source,int clr) {
        BufferedImage bi = null;//通过imageio将图像载入
        String destFileName = "";
        try {
            File srcFile = new File(source);
            bi = ImageIO.read(srcFile);
            int h = bi.getHeight();//获取图像的高
            int w = bi.getWidth();//获取图像的宽
            int rgb = bi.getRGB(0, 0);//获取指定坐标的ARGB的像素值
            int[][] gray = new int[w][h];
            BufferedImage binaryBufferedImage = new BufferedImage(w, h,
                    BufferedImage.TYPE_BYTE_BINARY);
            Map map = getMin(bi,w,h,clr);
            int minx = Integer.parseInt(map.get("minX").toString());
            int miny = Integer.parseInt(map.get("minY").toString());
            for (int y = miny; y < h; y++) {
                for (int x = minx; x < w; x++) {
                    if (bi.getRGB(x, y) == clr) {
                        Color color = new Color(bi.getRGB(x, y));
                        System.out.print("*");
                        binaryBufferedImage.setRGB(x - minx + 3, y - miny + 3, -1);
                    } else {
                        Color color = new Color(bi.getRGB(x, y));
                        System.out.print(" ");
                        gray[x][y] = -1;
//                        binaryBufferedImage.setRGB(x - minx + 3, y - miny + 3,0);
                    }
//                System.out.print(bi.getRGB(x,y));
                }
                System.out.println("");
            }
            File destFile = new File(StringUtils.substringBeforeLast(source,".") + "_tmp1.jpg");
            destFileName = destFile.getAbsolutePath();
            ImageIO.write(binaryBufferedImage, "jpg", destFile);
            srcFile.delete();
            System.out.println("--------------------------------------------------------");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return destFileName;
    }


    private static Map getMin(BufferedImage binaryBufferedImage,int w,int h,int clr) {
        int minX = w;
        int minY = h;
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int tmp = binaryBufferedImage.getRGB(x,y);
                System.out.print(tmp);
                if(tmp == clr && minX > x) {
                    minX = x;
                }
                if(tmp == clr && minY > y) {
                    minY = y;
                }
            }
            System.out.println();
        }
        return ImmutableMap.of("minX",minX,"minY",minY);
    }
}
