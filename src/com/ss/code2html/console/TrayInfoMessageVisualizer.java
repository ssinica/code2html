package com.ss.code2html.console;

import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;

public class TrayInfoMessageVisualizer implements InfoMessageVisualizer {

	private TrayIcon trayIcon;

	public TrayInfoMessageVisualizer(TrayIcon trayIcon) {
		this.trayIcon = trayIcon;
	}

	@Override
	public void showInfoMessage(String message) {
		trayIcon.displayMessage(null, message, MessageType.INFO);
	}

}
