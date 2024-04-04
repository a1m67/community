package com.nowcoder.community;

import java.io.IOException;

public class WKTests {
    public static void main(String[] args) {
        String cmd = "D:/work/wkhtmltopdf/bin/wkhtmltoimage --quality 75 3.cn D:/work/data/wk-image/test_e.png";
        try {
            // 并发执行
            Runtime.getRuntime().exec(cmd);
            System.out.println("ok");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
