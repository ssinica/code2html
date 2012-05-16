package com.ss.code2html.engine.theme;

import com.ss.code2html.engine.IHtmlTheme;
import com.ss.code2html.engine.Theme;

public class DarkHtmlTheme implements IHtmlTheme {

	@Override
	public Theme getTheme() {
		return Theme.DARK;
	}

	@Override
	public String getReservedWordStyle() {
		return "color:yellow;";
	}

	@Override
	public String getNormalTextStyle() {
		return "color:#fff;";
	}

	@Override
	public String getImportantTextStyle() {
		return "color:green;";
	}

	@Override
	public String getStringStyle() {
		return "color:blue;";
	}

	@Override
	public String getCommentStyle() {
		return "color:#999;";
	}

	@Override
	public String getBorderColor() {
		return "#666";
	}

	@Override
	public String getBackgroundColor() {
		return "#000";
	}

	@Override
	public String getTrademarkColor() {
		return "#999";
	}

}
