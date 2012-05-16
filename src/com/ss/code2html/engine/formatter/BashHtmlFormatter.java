package com.ss.code2html.engine.formatter;

import com.ss.code2html.engine.IHtmlTheme;

public class BashHtmlFormatter extends AbstractHtmlFormatter {

	@Override
	public FormatCtx createCtx() {
		return new Ctx();
	}

	@Override
	public FormatCtx formatLineOfCode(String line, IHtmlTheme theme, FormatCtx formatCtx) {
		Ctx ctx = (Ctx)formatCtx;
		return ctx;
	}

	private static class Ctx extends AbstractFormatCtx {

	}

}
