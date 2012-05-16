package com.ss.code2html.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import com.ss.code2html.engine.IHtmlFormatter;
import com.ss.code2html.engine.IHtmlTheme;

public class Code2HtmlTestImpl implements Code2HtmlTest {

	private String testName;
	private String inFileName;
	private String outFileName;
	private IHtmlFormatter formatter;
	private IHtmlTheme theme;

	public Code2HtmlTestImpl(String testName, String inFileName, String outFileName, IHtmlFormatter formatter, IHtmlTheme theme) {
		this.testName = testName;
		this.inFileName = inFileName;
		this.outFileName = outFileName;
		this.formatter = formatter;
		this.theme = theme;
	}

	@Override
	public void runTest() {
		try {
			System.out.println("Running test: " + getTestName());
            BufferedReader reader = new BufferedReader(new FileReader("./test/in/" + getInFileName()));

			IHtmlFormatter formatter = getFormatter();
			IHtmlTheme theme = getTheme();

            System.out.println("      Formating " + getInFileName() + " with theme: " + theme.getTheme().name());
            String formattedText = formatter.format(reader, theme);

            StringBuilder buf = new StringBuilder();
            buf.append("<html><body>");
            buf.append(formattedText);
            buf.append("</body></html>");

            BufferedWriter bw = new BufferedWriter(new FileWriter(new File("./test/out/" + getOutFileName())));
			System.out.println("      Results written to " + getOutFileName());
            bw.write(buf.toString());
            bw.close();
			System.out.println("      Success!");
        } catch (Exception e) {
            e.printStackTrace();
			System.out.println("      Failed!");
        }
	}

	@Override
	public String getTestName() {
		return testName;
	}

	@Override
	public String getInFileName() {
		return inFileName;
	}

	@Override
	public String getOutFileName() {
		return outFileName;
	}

	@Override
	public IHtmlFormatter getFormatter() {
		return formatter;
	}

	@Override
	public IHtmlTheme getTheme() {
		return theme;
	}

}
