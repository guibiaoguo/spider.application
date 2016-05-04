package spider.application.ocr.util;

import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2016/5/3.
 */
public class ClearImageHelper {

    private BufferedImage bufferedImage;
    private int w;
    private int h;

    public ClearImageHelper(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
        w = bufferedImage.getWidth();
        h = bufferedImage.getHeight();
    }

    public void printImage(int clr) {

        try {
            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    if (bufferedImage.getRGB(x, y) == clr)
                        System.out.print("*");
                    else
                        System.out.print(" ");
                }
                System.out.println();
            }
            System.out.println("----------------------------------------------------------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void filteNoise(int clr,String dstfile, String source) {

        File dtFile = new File(dstfile);
        if(!dtFile.exists())
            dtFile.mkdir();
        int gray[][] = new int[w][h];
        BufferedImage binaryBufferedImage = new BufferedImage(w, h,
                BufferedImage.TYPE_BYTE_BINARY);
        try {
            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    if (bufferedImage.getRGB(x, y) == clr) {
                        gray[x][y] = clr;
                        binaryBufferedImage.setRGB(x,y,clr);
                    }
                    else {
                        gray[x][y] = -1;
                        binaryBufferedImage.setRGB(x,y,-1);
                    }
                }
                ImageIO.write(binaryBufferedImage, "jpg", new File(dstfile,StringUtils.substringBeforeLast(source, ".") + ".png"));
            }
            System.out.println("----------------------------------------------------------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
