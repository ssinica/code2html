package com.ss.code2html.engine.theme;

import com.ss.code2html.engine.Code2HtmlFactory;
import com.ss.code2html.engine.IHtmlTheme;

public abstract class AbstractHtmlTheme implements IHtmlTheme {

	@Override
	public String getSnippetWidth() {
		return Code2HtmlFactory.getSnippetWidth();
	}

}
