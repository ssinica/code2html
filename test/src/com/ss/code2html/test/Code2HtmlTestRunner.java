package com.ss.code2html.test;

import com.ss.code2html.engine.formatter.CssHtmlFormatter;
import com.ss.code2html.engine.formatter.HtmlHtmlFormatter;
import com.ss.code2html.engine.theme.DarkHtmlTheme;
import com.ss.code2html.engine.theme.DefaultHtmlTheme;

public class Code2HtmlTestRunner {

	public static void main(String[] args) {
		new Code2HtmlTestRunner();
	}

	public Code2HtmlTestRunner() {
		new Code2HtmlTestImpl("Css2Default", "test.css", "css2default.html", new CssHtmlFormatter(), new DefaultHtmlTheme()).runTest();
		new Code2HtmlTestImpl("Css2Dark", "test.css", "css2dark.html", new CssHtmlFormatter(), new DarkHtmlTheme()).runTest();
		new Code2HtmlTestImpl("Html2Dark", "html2.html", "html2dark.html", new HtmlHtmlFormatter(), new DarkHtmlTheme()).runTest();
		new Code2HtmlTestImpl("Html2Default", "html2.html", "html2default.html", new HtmlHtmlFormatter(), new DefaultHtmlTheme()).runTest();
	}

}
