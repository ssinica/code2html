package com.ss.code2html.engine;

import java.util.HashMap;

import com.ss.code2html.engine.formatter.CssHtmlFormatter;
import com.ss.code2html.engine.formatter.TextHtmlFormatter;
import com.ss.code2html.engine.theme.DefaultHtmlTheme;

public class Code2HtmlFactory {

    private static IHtmlFormatter textFormatter;
    private static IHtmlFormatter cssFormatter;
	private static IHtmlTheme defaultTheme;
	private static HashMap<CodeType, Theme> codeType2Theme = new HashMap<CodeType, Theme>();

    private Code2HtmlFactory() {
        
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
        Theme theme = codeType2Theme.get(codeType);
        if(theme == null) {
			return getDefaultTheme();
        }
		switch (theme) {
		case DEFAULT:
			return getDefaultTheme();
		default:
			return getDefaultTheme();
		}
    }

	public static void setTheme(CodeType codeType, Theme theme) {
		codeType2Theme.put(codeType, theme);
    }

	private static IHtmlTheme getDefaultTheme() {
		if (defaultTheme == null) {
			defaultTheme = new DefaultHtmlTheme();
		}
		return defaultTheme;
	}

}
