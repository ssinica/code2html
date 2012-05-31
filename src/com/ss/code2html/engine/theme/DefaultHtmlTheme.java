package com.ss.code2html.engine.theme;

import com.ss.code2html.engine.Theme;

public class DefaultHtmlTheme extends AbstractHtmlTheme {

    @Override
    public Theme getTheme() {
        return Theme.DEFAULT;
    }

    @Override
    public String getReservedWordStyle() {
        return "color:#D66674;";
    }

    @Override
    public String getNormalTextStyle() {
		return "color:#5490FF;";
    }

    @Override
    public String getImportantTextStyle() {
		return "color:#3F7F7F;";
    }

    @Override
    public String getStringStyle() {
		return "color:#666;";
    }

    @Override
    public String getCommentStyle() {
        return "color:#64B3F4;";
    }

	@Override
	public String getBorderColor() {
		return "#E1E1E8";
	}

	@Override
	public String getBackgroundColor() {
		return "#F7F7F9";
	}

	@Override
	public String getTrademarkColor() {
		return "#999";
	}

}
