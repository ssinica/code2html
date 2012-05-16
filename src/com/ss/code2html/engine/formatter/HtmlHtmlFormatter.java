package com.ss.code2html.engine.formatter;

import java.util.StringTokenizer;

import org.apache.commons.lang3.StringEscapeUtils;

import com.ss.code2html.engine.IHtmlTheme;
import com.ss.code2html.utils.Utils;

public class HtmlHtmlFormatter extends AbstractHtmlFormatter {

	@Override
	public FormatCtx createCtx() {
		return new Ctx();
	}

	@Override
	public FormatCtx formatLineOfCode(String line, IHtmlTheme theme, FormatCtx formatCtx) {
		Ctx ctx = (Ctx) formatCtx;
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

			String span = "";
			if (ctx.isLastTokenWasCommentStart() && !"--&gt;".equals(token)) {
				span = genSpan(token, theme.getCommentStyle());
			} else if (isTag(token, ctx)) {
				span = genSpan(token, theme.getImportantTextStyle());
			//	ctx.setLastTokenWasStyleParam(false);
			//} else if (isStyleParamName(token)) {
			//	span = genSpan(res[0], theme.getReservedWordStyle());
			//	ctx.setLastTokenWasStyleParam(true);
			} else if ("&lt;!--".equals(token)) {
				span = genSpan(token, theme.getCommentStyle());
				ctx.setLastTokenWasCommentStart(true);
			} else if ("--&gt;".equals(token)) {
				span = genSpan(token, theme.getCommentStyle());
				ctx.setLastTokenWasCommentStart(false);
			//} else if (ctx.isLastTokenWasStyleParam()) {
			//	span = genSpan(res[0], theme.getNormalTextStyle());
			//	if (res[0].endsWith(";") || res[1] != null
			//			&& ";".equals(res[1])) {
			//		ctx.setLastTokenWasStyleParam(false);
			//	}
			} else {
				
				// rel="stylesheet">
				StringBuffer tempBuf = new StringBuffer();
				
				boolean appendWithTagClose = false;
				int endIdx = token.length() - 4;
				if (token.endsWith("&gt;") && endIdx > 0) {
					token = token.substring(0, endIdx);
					appendWithTagClose = true;
				}

				int equalSignIdx = token.indexOf("=");
				if (equalSignIdx >= 0) {
					String part1 = token.substring(0, equalSignIdx);
					String part2 = token.substring(equalSignIdx + 1);
					
					if(!Utils.isEmpty(part1)) {
						tempBuf.append(genSpan(part1, theme.getReservedWordStyle()));
					}
					tempBuf.append(genSpan("=", theme.getNormalTextStyle()));

					if (!Utils.isEmpty(part2)) {
						int idx1 = part2.indexOf("&quot;");
						int idx2 = part2.lastIndexOf("&quot;");
						if (idx1 >= 0 && idx2 > 0 && idx1 != idx2) {
							String p1 = part2.substring(0, idx1);
							String p2 = part2.substring(idx1 + 6, idx2);
							String p3 = part2.substring(idx2 + 6);
						}
					}

				} else {
					tempBuf.append(genSpan(token, theme.getNormalTextStyle()));
				}
				if (appendWithTagClose) {
					tempBuf.append(genSpan("&gt;", theme.getImportantTextStyle()));
				}
				span = tempBuf.toString();
			}
			buf.append(span);

            tokenIdx += 1;
        }

        String s = wrapWithSpacesAddBr(buf.toString(), leftSpacesCount, rightSpacesCount);
        ctx.appendFormattedLine(s);
        return ctx;
	}

	public boolean isTag(String token, Ctx ctx) {
		return token.startsWith("&lt;") && !token.startsWith("&lt;!--")
				|| token.startsWith("&lt;/")
				|| "&gt;".equals(token);
	}

	private String prepareLine(String line) {
		if (Utils.isEmpty(line)) {
			return "";
		}

		line = line.replaceAll("<!--", "==C2H== <!-- ==C2H==");
		line = line.replaceAll("-->", "==C2H== --> ==C2H==");
		line = line.replaceAll(">", "> ==C2H==");
		line = line.replaceAll("<", "==C2H== <");
		line = line.replaceAll("==C2H== ", " ");
		line = line.replaceAll(" ==C2H==", " ");
		
		line = StringEscapeUtils.escapeHtml4(line);

		return line;
	}
	
	public static void main(String[] args) {
		System.out.println(new HtmlHtmlFormatter().prepareLine("sad\"asd\"sad<!--asd-->asd'as'd"));

		String token = "123567=";
		int equalSignIdx = token.indexOf("=");
		String part1 = token.substring(0, equalSignIdx);
		String part2 = token.substring(equalSignIdx + 1);
		System.out.println("1: " + part1 + ", 2: " + part2);

		String s = "123&quot;456&quot;789";
		int idx1 = part2.indexOf("&quot;");
		int idx2 = part2.lastIndexOf("&quot;");
		if (idx1 >= 0 && idx2 > 0 && idx1 != idx2) {
			String p1 = part2.substring(0, idx1);
			String p2 = part2.substring(idx1 + 6, idx2);
			String p3 = part2.substring(idx2 + 6);
			System.out.println(p1 + "|" + p2 + "|" + p3);
		}
	}

	private class Ctx extends AbstractFormatCtx {

		boolean lastTokenWasCommentStart = false;

		public boolean isLastTokenWasCommentStart() {
			return lastTokenWasCommentStart;
		}

		public void setLastTokenWasCommentStart(boolean lastTokenWasCommentStart) {
			this.lastTokenWasCommentStart = lastTokenWasCommentStart;
		}

	}

}
