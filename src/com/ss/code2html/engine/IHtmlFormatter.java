package com.ss.code2html.engine;

import java.io.BufferedReader;

public interface IHtmlFormatter {

    /**
     * Formats input code to html.
     * @param code
     * @return
     */
    public String format(BufferedReader code, IHtmlTheme theme);

}
