package shentong.spider.ocr.main;

import shentong.spider.ocr.util.ClearImageHelper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by Administrator on 2016/5/3.
 */
public class ImageHelper {

    private ClearImageHelper clearImageHelper;

    public void printImage (int clr) {
        try {
            File dstFile = new File("E:\\workspace\\spider.application\\spider.crawler\\target\\test-classes\\20160503");
            for(File file:dstFile.listFiles()) {
                if(file.isFile()) {
                    BufferedImage bufferedImage = ImageIO.read(file);
                    clearImageHelper = new ClearImageHelper(bufferedImage);
                    System.out.println("***********" + file.getName() + "**************");
                    clearImageHelper.printImage(clr);
                    clearImageHelper.filteNoise(-16777216,file.getParent() + "/tmp1" ,file.getName());
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void filterNoise() {

    }
}
