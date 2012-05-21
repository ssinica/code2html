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
			} else if ("&lt;!--".equals(token)) {
				span = genSpan(token, theme.getCommentStyle());
				ctx.setLastTokenWasCommentStart(true);
			} else if ("--&gt;".equals(token)) {
				span = genSpan(token, theme.getCommentStyle());
				ctx.setLastTokenWasCommentStart(false);
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
							
							if(!Utils.isEmpty(p1)) {
								tempBuf.append(genSpan(p1, theme.getNormalTextStyle()));
							}
							tempBuf.append(genSpan("&quot;", theme.getNormalTextStyle()));
							if(!Utils.isEmpty(p2)) {
								tempBuf.append(genSpan(p2, theme.getStringStyle()));
							}
							tempBuf.append(genSpan("&quot;", theme.getNormalTextStyle()));
							if(!Utils.isEmpty(p3)) {
								tempBuf.append(genSpan(p3, theme.getNormalTextStyle()));
							}
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
		s = s.replaceAll("==CLR==", " ");

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

		line = findPairDelims(line, "\"", new TransformCallback() {
			@Override
			public String transform(String str) {
				if (Utils.isEmpty(str)) {
					return "";
				} else {
					str = str.replaceAll(" ", "==CLR==");
					return str;
				}
			}
		});

		line = line.replaceAll("<!--", "==C2H== <!-- ==C2H==");
		line = line.replaceAll("-->", "==C2H== --> ==C2H==");
		line = line.replaceAll(">", "> ==C2H==");
		line = line.replaceAll("<", "==C2H== <");
		line = line.replaceAll("==C2H== ", " ");
		line = line.replaceAll(" ==C2H==", " ");
		
		line = StringEscapeUtils.escapeHtml4(line);

		return line;
	}
	
	private String findPairDelims(String str, String delim, TransformCallback callback) {
		StringBuilder sb = new StringBuilder();
		int delimLength = delim.length();
		int idx = str.indexOf(delim);
		int lastWrittenIdx = 0;
		int delimCounter = 0;

		while (idx >= 0) {
			delimCounter += 1;
			String before = str.substring(lastWrittenIdx, idx);
			int beforeLength = before.length();
			if (delimCounter % 2 == 0) {
				before = callback.transform(before);
			}
			sb.append(before);
			sb.append(delim);
			lastWrittenIdx += beforeLength + delimLength;
			idx = str.indexOf(delim, lastWrittenIdx);
		}

		if (lastWrittenIdx < str.length()) {
			sb.append(str.substring(lastWrittenIdx, str.length()));
		}

		return sb.toString();
	}

	public static void main(String[] args) {
		
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

	public interface TransformCallback {
		String transform(String str);
	}
}
