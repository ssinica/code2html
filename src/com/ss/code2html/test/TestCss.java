package com.ss.code2html.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import com.ss.code2html.engine.Code2HtmlFactory;
import com.ss.code2html.engine.CodeType;
import com.ss.code2html.engine.IHtmlFormatter;
import com.ss.code2html.engine.IHtmlTheme;

public class TestCss {

    public static void main(String[] args) {
        new TestCss();
    }

    public TestCss() {

        try {
            BufferedReader reader = new BufferedReader(new FileReader("./test.css"));

            IHtmlFormatter formatter = Code2HtmlFactory.getHtmlFormatter(CodeType.CSS);
            IHtmlTheme theme = Code2HtmlFactory.getHtmlTheme(CodeType.CSS);

            System.out.println("Formating input with theme: " + theme.getTheme().name());
            String formattedText = formatter.format(reader, theme);

            StringBuilder buf = new StringBuilder();
            buf.append("<html><body>");
            buf.append(formattedText);
            buf.append("</body></html>");

            BufferedWriter bw = new BufferedWriter(new FileWriter(new File("./css-test.html")));
            bw.write(buf.toString());
            bw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
