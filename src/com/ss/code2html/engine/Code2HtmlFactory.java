package com.ss.code2html.engine;

import com.ss.code2html.engine.css.CssHtmlFormatter;
import com.ss.code2html.engine.text.TextHtmlFormatter;
import com.ss.code2html.engine.theme.DefaultHtmlTheme;

public class Code2HtmlFactory {

    private static ICodeRecognizer codeRecognizer;
    private static IHtmlFormatter textFormatter;
    private static IHtmlFormatter cssFormatter;
    private static IHtmlTheme htmlTheme;

    private Code2HtmlFactory() {
        
    }
    
    public static ICodeRecognizer getCodeRegognizer() {
        if (codeRecognizer == null) {
            codeRecognizer = new CodeRecognizer();
        }
        return codeRecognizer;
    }
    
    public static IHtmlFormatter getHtmlFormatter(CodeType codeType) {
        switch (codeType) {
        case CSS:
            if (cssFormatter == null) {
                cssFormatter = new CssHtmlFormatter();
            }
            return cssFormatter;
        default:
            if (textFormatter == null) {
                textFormatter = new TextHtmlFormatter();
            }
            return textFormatter;
        }
    }
    
    public static IHtmlTheme getHtmlTheme(CodeType codeType) {
        if (htmlTheme == null) {
            htmlTheme = new DefaultHtmlTheme();
        }
        return htmlTheme;
    }

    public static void setCurrentTheme(Theme theme) {
        switch (theme) {
        case DEFAULT:
            Code2HtmlFactory.htmlTheme = new DefaultHtmlTheme();
            return;
        default:
            Code2HtmlFactory.htmlTheme = new DefaultHtmlTheme();
            return;
        }
    }

}
