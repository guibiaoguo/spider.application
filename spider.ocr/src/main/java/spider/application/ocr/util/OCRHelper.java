package spider.application.ocr.util;

/**
 * Created by Administrator on 2015/11/24.
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class OCRHelper {
    private final String LANG_OPTION = "-l";
    private final String PSM = "-psm";
    private final String EOL = System.getProperty("line.separator");
    private String language = "eng";
    /**
     * 文件位置我防止在，项目同一路径
     */
    private String tessPath = new File("D:\\Program Files\\Tesseract-OCR").getAbsolutePath();

    public OCRHelper() {

    }

    public OCRHelper(String language) {
        this.language = language;
    }
    /**
     * @param imageFile 传入的图像文件
     *                  //* @param imageFormat
     *                  传入的图像格式
     * @return 识别后的字符串
     */
    public String recognizeText(File imageFile) throws Exception {
        /**
         * 设置输出文件的保存的文件目录
         */
        File outputFile = new File(imageFile.getParentFile(), "output");

        StringBuffer strB = new StringBuffer();
        List<String> cmd = new ArrayList<String>();
//        if (OS.isWindowsXP()) {
//            cmd.add(tessPath + "\\tesseract");
//        } else if (OS.isLinux()) {
//            cmd.add("tesseract");
//        } else {
//            cmd.add(tessPath + "\\tesseract");
//        }
        cmd.add(tessPath + "\\tesseract");
        cmd.add("");
        cmd.add(outputFile.getName());
        cmd.add(LANG_OPTION);
//		cmd.add("chi_sim");
        cmd.add(language);
        ProcessBuilder pb = new ProcessBuilder();
        /**
         *Sets this process builder's working directory.
         */
        pb.directory(imageFile.getParentFile());
        cmd.set(1, imageFile.getName());
        pb.command(cmd);
        pb.redirectErrorStream(true);
        Process process = pb.start();
        // tesseract.exe 1.jpg 1 -l chi_sim
        // Runtime.getRuntime().exec("tesseract.exe 1.jpg 1 -l chi_sim");
        /**
         * the exit value of the process. By convention, 0 indicates normal
         * termination.
         */
//		System.out.println(cmd.toString());
        int w = process.waitFor();
        if (w == 0)// 0代表正常退出
        {
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    new FileInputStream(outputFile.getAbsolutePath() + ".txt"),
                    "UTF-8"));
            String str;

            while ((str = in.readLine()) != null) {
                strB.append(str).append(EOL);
            }
            in.close();
        } else {
            String msg;
            switch (w) {
                case 1:
                    msg = "Errors accessing files. There may be spaces in your image's filename.";
                    break;
                case 29:
                    msg = "Cannot recognize the image or its selected region.";
                    break;
                case 31:
                    msg = "Unsupported image format.";
                    break;
                default:
                    msg = "Errors occurred.";
            }
            throw new RuntimeException(msg);
        }
        new File(outputFile.getAbsolutePath() + ".txt").delete();
        return strB.toString().replaceAll("\\s*", "");
    }

    public static void main(String[] args) {
        try {

            File testDataDir = new File("E:\\workspace\\spider.application\\spider.crawler\\target\\test-classes\\20160503\\tmp1");
            System.out.println(testDataDir.listFiles().length);
            int i = 0;
            int k=0;
//            Pattern pattern = Pattern.compile("^[零,一,二,三,四,五,六,七,八,九,十,靈,壹,贰,叁,肆,伍,陆,柒,捌,玖,拾,0-9]");
//            Matcher matcher = pattern.matcher("123456$wefwfe".replaceAll("[^0-9]",""));
//            if(matcher.find())
//                System.out.println("true");
            long begintime = System.currentTimeMillis();
            for (File file : testDataDir.listFiles()) {
                i++;
                String recognizeText = new OCRHelper().recognizeText(file).trim()
                        .replaceAll("a","8")
                        .replaceAll("n","0")
                        .replaceAll("e","9")
                        .replaceAll("t","1")
                        .replaceAll("ﬁ","8")
                        .replaceAll("s","6")
                        .replaceAll("u","0")
                        .replaceAll("o","0")
                        .replaceAll("z","2")
                        ;
                System.out.print("FiLE:" + file.getName() + " :" + recognizeText + "\n");
//                matcher = pattern.matcher(recognizeText);
//                if(matcher.find()) {
//                    System.out.println("---------------------------------");
//                    System.out.print("FiLE:" + file.getName() + " :" + recognizeText + "\n");
//                    System.out.println("----------------------------------");
//                    k++;
//                } else {
////                    file.delete();
//                }
            }
            long endtime = System.currentTimeMillis();
            System.out.println(k);
            System.out.println(i);
            System.out.println(k*1.0/i);
            System.out.println("left time " + (endtime - begintime));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

