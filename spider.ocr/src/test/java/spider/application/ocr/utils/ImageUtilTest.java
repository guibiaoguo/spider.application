package spider.application.ocr.utils;

import org.junit.Test;

import java.io.File;

/**
 * Created by Bill on 2016/5/1.
 */
public class ImageUtilTest {

    private ImageUtil imageUtil = new ImageUtil();

    @Test
    public void convert() {
        imageUtil.convert("E:\\ideaworkspace\\spider.application\\spider.crawler\\target\\test-classes\\20160501\\1.png","jpg","E:\\ideaworkspace\\spider.application\\spider.crawler\\target\\test-classes\\20160501\\2.jpg");
    }

    @Test
    public void printImage() {
        File destFile = new File("E:\\ideaworkspace\\spider.application\\spider.crawler\\target\\test-classes\\20160502\\");
        for (File file:destFile.listFiles()) {
            if(!file.isDirectory())
                imageUtil.printImage(file.getAbsolutePath());
        }
//        imageUtil.printImage("E:\\ideaworkspace\\spider.application\\spider.crawler\\target\\test-classes\\20160501\\1.png");
    }
}
