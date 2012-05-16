package com.ss.code2html.test;

import com.ss.code2html.engine.IHtmlFormatter;
import com.ss.code2html.engine.IHtmlTheme;

public interface Code2HtmlTest {

	String getTestName();

	String getInFileName();

	String getOutFileName();

	IHtmlFormatter getFormatter();

	IHtmlTheme getTheme();

	void runTest();

}
