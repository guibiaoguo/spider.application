package spider.application.ocr.util;

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

    public void printImage (int clr) {

        try {
            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    if(bufferedImage.getRGB(x,y) == clr )
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

    public void filteNoise (int clr) {
        int gray[][] = new int[w][h];
        BufferedImage binaryImage = new BufferedImage();
        try {
            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    if(bufferedImage.getRGB(x,y) == clr )
                        gray[x][y] = clr;
                    else
                        gray[x][y] = -1;
                }
                ImageIO.write(bufferedImage, "png", new File( + ".png"));
            }
            System.out.println("----------------------------------------------------------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
