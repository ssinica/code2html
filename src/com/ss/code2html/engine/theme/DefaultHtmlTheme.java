package com.ss.code2html.engine.theme;

import com.ss.code2html.engine.IHtmlTheme;
import com.ss.code2html.engine.Theme;

public class DefaultHtmlTheme implements IHtmlTheme {

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
        return "color:#666;";
    }

    @Override
    public String getImportantTextStyle() {
        //return "color:#3F7F7F;";
        return "color:green;";
    }

    @Override
    public String getStringStyle() {
        return "color:#5490FF;";
    }

    @Override
    public String getCommentStyle() {
        return "color:#64B3F4;";
    }

}
