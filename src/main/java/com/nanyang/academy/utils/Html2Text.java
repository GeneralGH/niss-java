package com.nanyang.academy.utils;

/**
 * @ClassName pt
 * @Description TODO
 * @Author pt
 * @Date 2022/10/8
 * @Version 1.0
 **/
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;

public class Html2Text extends HTMLEditorKit.ParserCallback {

    private static Html2Text html2Text = new Html2Text();

    StringBuffer s;

    public Html2Text() {
    }

    public void parse(String str) throws IOException {

        InputStream iin = new ByteArrayInputStream(str.getBytes());
        Reader in = new InputStreamReader(iin);
        s = new StringBuffer();
        ParserDelegator delegator = new ParserDelegator();
        // the third parameter is TRUE to ignore charset directive
        delegator.parse(in, this, Boolean.TRUE);
        iin.close();
        in.close();
    }

    public void handleText(char[] text, int pos) {
        s.append(text);
    }

    public String getText() {
        return s.toString();
    }

    public static String getContent(String str) {
        try {
            html2Text.parse(str);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return html2Text.getText();
    }

    public static void main(String[] args) {
        String text = "<p>中文开始，<br></p><div data-w-e-type=\"video\" data-w-e-is-void>\n" +
                "<video poster=\"\" controls=\"true\" width=\"auto\" height=\"auto\"><source src=\"http://192.168.0.75:8070/profile/upload/2022/08/11/68d3e85cc3d474d3ae8d3475dd3e8b21_20220811135003A001.mp4\" type=\"video/mp4\"/></video>\n" +
                "</div><p><br></p><p><br>结尾。</p>";
        text = Html2Text.getContent(text);
        System.out.println(text);
    }
}