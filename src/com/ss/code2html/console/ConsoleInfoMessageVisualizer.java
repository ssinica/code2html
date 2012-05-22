package com.ss.code2html.console;

public class ConsoleInfoMessageVisualizer implements InfoMessageVisualizer {

	@Override
	public void showInfoMessage(String message) {
		System.out.println(message);
	}

}
