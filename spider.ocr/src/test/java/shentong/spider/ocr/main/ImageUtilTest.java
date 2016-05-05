package shentong.spider.ocr.main;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import shentong.spider.ocr.util.ImageUtil;

import java.io.File;

/**
 * Created by Bill on 2016/5/1.
 */
public class ImageUtilTest {

    private ImageUtil imageUtil = new ImageUtil();

    @Test
    public void convert() {
        imageUtil.convert("E:\\ideaworkspace\\spider.application\\spider.crawler\\target\\test-classes\\20160504\\1.png","jpg","E:\\ideaworkspace\\spider.application\\spider.crawler\\target\\test-classes\\20160501\\2.jpg");
    }

    @Test
    public void printImage() {
        File destFile = new File("E:\\workspace\\spider.application\\spider.crawler\\target\\test-classes\\20160504");
        for (File file:destFile.listFiles()) {
            if(file.isFile() && StringUtils.indexOf(file.getName(),".") > -1)
                imageUtil.printImage(file.getAbsolutePath(),-16777216);
        }
//        imageUtil.printImage("E:\\ideaworkspace\\spider.application\\spider.crawler\\target\\test-classes\\20160501\\1.png");
    }
}
