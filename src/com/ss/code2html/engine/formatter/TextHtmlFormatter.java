package com.ss.code2html.engine.formatter;

import java.io.BufferedReader;

import com.ss.code2html.engine.IHtmlFormatter;
import com.ss.code2html.engine.IHtmlTheme;

public class TextHtmlFormatter implements IHtmlFormatter {

    @Override
    public String format(BufferedReader reader, IHtmlTheme theme) {
        try {
            StringBuilder buf = new StringBuilder();
            String line = reader.readLine();
            while (line != null) {
                buf.append(line);
                line = reader.readLine();
            }
            return buf.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "exception while parsing";
        }
    }

}
