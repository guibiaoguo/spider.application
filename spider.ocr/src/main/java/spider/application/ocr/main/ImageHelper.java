package spider.application.ocr.main;

import spider.application.ocr.util.ClearImageHelper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by Administrator on 2016/5/3.
 */
public class ImageHelper {

    private ClearImageHelper clearImageHelper;

    public void printImage () {
        try {
            File dstFile = new File("E:\\workspace\\spider.application\\spider.crawler\\target\\test-classes\\20160503");
            for(File file:dstFile.listFiles()) {
                if(file.isFile()) {
                    BufferedImage bufferedImage = ImageIO.read(file);
                    clearImageHelper = new ClearImageHelper(bufferedImage);
                    System.out.println("***********" + file.getName() + "**************");
                    clearImageHelper.printImage(-16777216);
                }
            }
        }catch (Exception e) {

        }
    }
}
