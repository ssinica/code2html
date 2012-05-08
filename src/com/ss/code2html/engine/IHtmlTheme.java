package com.ss.code2html.engine;

public interface IHtmlTheme {

    Theme getTheme();

    String getReservedWordStyle();

    String getNormalTextStyle();

    String getImportantTextStyle();

    String getStringStyle();

    String getCommentStyle();

	String getBorderColor();

	String getBackgroundColor();

	String getTrademarkColor();

}
