package com.ss.code2html.engine.formatter;

public class AbstractFormatCtx implements FormatCtx {

	private StringBuffer buf = new StringBuffer();
	private static final String NEW_LINE = System.getProperty("line.separator");

	@Override
	public void appendFormattedLine(String formattedLine) {
		buf.append(formattedLine);
		buf.append(NEW_LINE);
	}

	@Override
	public String getFormattedLine() {
		return buf.toString();
	}

}
