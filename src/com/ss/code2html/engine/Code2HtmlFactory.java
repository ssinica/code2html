package com.ss.code2html.engine;

import java.util.HashMap;

import com.ss.code2html.engine.formatter.CssHtmlFormatter;
import com.ss.code2html.engine.formatter.HtmlHtmlFormatter;
import com.ss.code2html.engine.formatter.TextHtmlFormatter;
import com.ss.code2html.engine.theme.DarkHtmlTheme;
import com.ss.code2html.engine.theme.DefaultHtmlTheme;

public class Code2HtmlFactory {

    private static IHtmlFormatter textFormatter;
    private static IHtmlFormatter cssFormatter;
	private static IHtmlFormatter htmlFormatter;
	private static IHtmlTheme defaultTheme;
	private static IHtmlTheme darkTheme;
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
		case HTML:
			if (htmlFormatter == null) {
				htmlFormatter = new HtmlHtmlFormatter();
			}
			return htmlFormatter;
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
		case DARK:
			return getDarkTheme();
		default:
			return getDefaultTheme();
		}
    }

	private static IHtmlTheme getDarkTheme() {
		if (darkTheme == null) {
			darkTheme = new DarkHtmlTheme();
		}
		return darkTheme;
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
