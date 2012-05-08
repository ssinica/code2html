package com.ss.code2html.engine.css;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;

import com.ss.code2html.engine.IHtmlFormatter;
import com.ss.code2html.engine.IHtmlTheme;
import com.ss.code2html.utils.Utils;

public class CssHtmlFormatter implements IHtmlFormatter {

    @Override
    public String format(BufferedReader reader, IHtmlTheme theme) {
        String code = formatImpl(reader, theme);
        return wrap(code, theme);
    }

    private String wrap(String code, IHtmlTheme theme) {
        StringBuilder sb = new StringBuilder();

		sb.append("<div style='font-size:12px;line-height:1.3;padding:7px;border:1px solid "
				+ theme.getBorderColor()
				+ ";-moz-border-radius: 3px;-webkit-border-radius: 3px;border-radius: 3px;position:relative;background-color: "
				+ theme.getBackgroundColor() + ";'>");
		sb.append(genCopyrights(theme));
        sb.append(code);
        sb.append("</div>");

        return sb.toString();
    }

	private String genCopyrights(IHtmlTheme theme) {
        StringBuilder sb = new StringBuilder();
        sb.append("<div style='font-size:10px;color:" + theme.getTrademarkColor() + ";font-style:italic;position:absolute;top:5px;right:10px;'>");
		sb.append("<span>Powered by</span>&nbsp;<a style='color:"
				+ theme.getTrademarkColor()
				+ ";' href='https://github.com/ssinica/code2html'>https://github.com/ssinica/code2html</a>");
        sb.append("</div>");
        return sb.toString();
    }

    private String formatImpl(BufferedReader reader, IHtmlTheme theme) {
        try {
            String line = reader.readLine();
            Ctx ctx = new Ctx();
            while (line != null) {
                ctx = formatLineOfCode(line, theme, ctx);
                line = reader.readLine();
            }
            return ctx.getFormattedLine();
        } catch (IOException e) {
            System.out.println("Exception while formating code");
            return "failed to parse";
        }
    }

    private Ctx formatLineOfCode(String line, IHtmlTheme theme, Ctx ctx) {
        int originalLength = line.length();
        line = trimLeft(line);
        int leftSpacesCount = originalLength - line.length();
        line = trimRight(line);
        int rightSpacesCount = originalLength - leftSpacesCount - line.length();

        StringBuffer buf = new StringBuffer();
        line = prepareLine(line);
        StringTokenizer tokenizer = new StringTokenizer(line, " ");
        int tokenIdx = 0;
        while (tokenizer.hasMoreTokens()) {
            if (tokenIdx > 0) {
                buf.append("&nbsp;");
            }
            String token = tokenizer.nextToken();
            String[] res = checkForRigthPunctuation(token);

            String span = "";
            if (ctx.isLastTokenWasCommentStart() && !"*/".equals(res[0])) {
                span = genSpan(res[0], theme.getCommentStyle());
            } else if (isClassName(token, ctx)) {
                span = genSpan(res[0], theme.getImportantTextStyle());
                ctx.setLastTokenWasStyleParam(false);
            } else if (isStyleParamName(token)) {
                span = genSpan(res[0], theme.getReservedWordStyle());
                ctx.setLastTokenWasStyleParam(true);
            } else if ("/*".equals(res[0])) {
                span = genSpan(res[0], theme.getCommentStyle());
                ctx.setLastTokenWasCommentStart(true);
            } else if ("*/".equals(res[0])) {
                span = genSpan(res[0], theme.getCommentStyle());
                ctx.setLastTokenWasCommentStart(false);
            } else if (ctx.isLastTokenWasStyleParam()) {
                span = genSpan(res[0], theme.getStringStyle());
                if (res[0].endsWith(";") || res[1] != null && ";".equals(res[1])) {
                    ctx.setLastTokenWasStyleParam(false);
                }
            } else {
                span = genSpan(res[0], theme.getNormalTextStyle());
                ctx.setLastTokenWasStyleParam(false);
            }
            buf.append(span);

            if (res[1] != null) {
                if (ctx.isLastTokenWasCommentStart()) {
                    buf.append(genSpan(res[1], theme.getCommentStyle()));
                } else {
                    buf.append(genSpan(res[1], theme.getNormalTextStyle()));
                }
            }

            tokenIdx += 1;
        }

        String s = wrapWithSpacesAddBr(buf.toString(), leftSpacesCount, rightSpacesCount);
        ctx.appendFormattedLine(s);
        ctx.setLastTokenWasStyleParam(false);
        return ctx;
    }

    private String[] checkForRigthPunctuation(String token) {
        String[] ret = new String[2];
        if(Utils.isEmpty(token) || token.length() < 2) {
            ret[0] = token;
            return ret;
        }        
        if (token.endsWith(":") || token.endsWith(",") || token.endsWith(";")) {
            ret[0] = token.substring(0, token.length() - 1);
            ret[1] = token.substring(token.length() - 1);
        } else {
            ret[0] = token;
        }
        return ret;
    }

    private boolean isStyleParamName(String token) {
        return token.endsWith(":");
    }

    private boolean isClassName(String token, Ctx ctx) {
        return token.startsWith(".") || token.startsWith("#") && !ctx.isLastTokenWasStyleParam();
    }

    private String genSpan(String text, String style) {
        return "<span style='" + style + "'>" + text + "</span>";
    }

    private String wrapWithSpacesAddBr(String line, int left, int right) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < left; i++) {
            buf.append("&nbsp;");
        }
        buf.append(line);
        for (int i = 0; i < right; i++) {
            buf.append("&nbsp;");
        }
        buf.append("<br>");
        return buf.toString();
    }

    private String trimRight(String value) {
        return value.replaceAll("\\s*$", "");
    }

    private String trimLeft(String value) {
        return value.replaceAll("^\\s*", "");
    }

    private String prepareLine(String line) {
        if (Utils.isEmpty(line)) {
            return "";
        }
        line = line.replaceAll("/\\*", "<--> /* <-->");
        line = line.replaceAll("\\*/", "<--> */ <-->");
        line = line.replaceAll(":", ": <-->");
        line = line.replaceAll("\\{", " { <-->");
        line = line.replaceAll("\\}", " } <-->");
        line = line.replaceAll("<--> ", " ");
        line = line.replaceAll(" <-->", " ");
        return line;
    }

    public static void main(String[] args) {
        //System.out.println(new CssHtmlFormatter().trimLeft("   asdsadasd   "));
        //System.out.println(new CssHtmlFormatter().trimRight("   asdsadasd   "));
        //String[] res = new CssHtmlFormatter().checkForRigthPunctuation(",");
        //System.out.println("[" + res[0] + "," + res[1] + "]");
        System.out.println(new CssHtmlFormatter().prepareLine("123 /*    lkj*/        123/* 123*/"));
    }

    // ----------------------------------------------------------------------

    private class Ctx {
        private boolean lastTokenWasCommentStart = false;
        private StringBuffer buf = new StringBuffer();
        boolean lastTokenWasStyleParam = false;

        public Ctx() {

        }

        public void setLastTokenWasCommentStart(boolean lastTokenWasCommentStart) {
            this.lastTokenWasCommentStart = lastTokenWasCommentStart;
        }

        public boolean isLastTokenWasCommentStart() {
            return lastTokenWasCommentStart;
        }

        public void appendFormattedLine(String formattedLine) {
            buf.append(formattedLine);
        }

        public String getFormattedLine() {
            return buf.toString();
        }

        public boolean isLastTokenWasStyleParam() {
            return lastTokenWasStyleParam;
        }

        public void setLastTokenWasStyleParam(boolean lastTokenWasStyleParam) {
            this.lastTokenWasStyleParam = lastTokenWasStyleParam;
        }
    }
}
