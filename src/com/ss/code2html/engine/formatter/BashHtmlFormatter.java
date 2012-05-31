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
		int originalLength = line.length();
		line = trimLeft(line);
		int leftSpacesCount = originalLength - line.length();
		line = trimRight(line);
		int rightSpacesCount = originalLength - leftSpacesCount - line.length();

		StringBuffer buf = new StringBuffer();
		if (!"".equals(line)) {
			buf.append(genSpan("$ ", theme.getImportantTextStyle()));
			if (line.startsWith("$")) {
				line = line.substring(1);
			}
			if (line.startsWith("$ ")) {
				line = line.substring(2);
			}
			buf.append(genSpan(line, theme.getNormalTextStyle()));
		}
		
		String s = wrapWithSpacesAddBr(buf.toString(), leftSpacesCount, rightSpacesCount);
        ctx.appendFormattedLine(s);
		return ctx;
	}

	private static class Ctx extends AbstractFormatCtx {

	}

}
