package com.ss.code2html.engine.formatter;

public class AbstractFormatCtx implements FormatCtx {

	private StringBuffer buf = new StringBuffer();

	@Override
	public void appendFormattedLine(String formattedLine) {
		buf.append(formattedLine);
	}

	@Override
	public String getFormattedLine() {
		return buf.toString();
	}

}
