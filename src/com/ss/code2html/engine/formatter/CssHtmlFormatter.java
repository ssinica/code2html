package com.ss.code2html.engine.formatter;

import java.util.StringTokenizer;

import com.ss.code2html.engine.IHtmlTheme;
import com.ss.code2html.utils.Utils;

public class CssHtmlFormatter extends AbstractHtmlFormatter {

	@Override
	public FormatCtx createCtx() {
		return new Ctx();
	}

	@Override
    public Ctx formatLineOfCode(String line, IHtmlTheme theme, FormatCtx formatCtx) {
		Ctx ctx = (Ctx)formatCtx;
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
				span = genSpan(res[0], theme.getNormalTextStyle());
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

    private boolean isStyleParamName(String token) {
        return token.endsWith(":");
    }

    private boolean isClassName(String token, Ctx ctx) {
        return token.startsWith(".") || token.startsWith("#") && !ctx.isLastTokenWasStyleParam();
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

	private class Ctx extends AbstractFormatCtx {
		private boolean lastTokenWasCommentStart = false;
        boolean lastTokenWasStyleParam = false;

        public void setLastTokenWasCommentStart(boolean lastTokenWasCommentStart) {
            this.lastTokenWasCommentStart = lastTokenWasCommentStart;
        }

        public boolean isLastTokenWasCommentStart() {
            return lastTokenWasCommentStart;
		}

        public boolean isLastTokenWasStyleParam() {
            return lastTokenWasStyleParam;
        }

        public void setLastTokenWasStyleParam(boolean lastTokenWasStyleParam) {
            this.lastTokenWasStyleParam = lastTokenWasStyleParam;
        }
    }
}
