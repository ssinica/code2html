package com.ss.code2html.console;

import java.awt.AWTException;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.JIntellitype;
import com.ss.code2html.engine.Code2HtmlFactory;
import com.ss.code2html.engine.CodeType;
import com.ss.code2html.engine.IHtmlFormatter;
import com.ss.code2html.engine.IHtmlTheme;
import com.ss.code2html.engine.Theme;
import com.ss.code2html.utils.Utils;

public class Code2HtmlTray implements Runnable, HotkeyListener, ClipboardOwner {

	private List<CodeType> codeTypes = new ArrayList<CodeType>();
	private InfoMessageVisualizer infoMessageVisualizer;
	private TrayIcon trayIcon;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Code2HtmlTray());
	}

	@Override
	public void run() {
		if (SystemTray.isSupported()) {
			runWithTray();
			infoMessageVisualizer = new TrayInfoMessageVisualizer(trayIcon);
		} else {
			runNoTray();
			infoMessageVisualizer = new ConsoleInfoMessageVisualizer();
		}

		try {
			init();
		} catch (Exception e) {
			System.out.println("Failed to init code2html");
			exit();
		}
	}

	private void exit() {
		JIntellitype.getInstance().cleanUp();
		System.exit(0);
	}

	private void runNoTray() {
		try {
			runConsole();
		} finally {
			exit();
		}
	}

	private void runConsole() {
		System.out.println("Code2Html is application to convert code to html.");
        System.out.println("Copy code to clipboard, press Ctrl+Shift+XXX and paste HTML to your destination.");
        System.out.println("Type 'exit', to exit.");
		while (true) {
            try {
                if (System.in.available() > 0) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                    String line = in.readLine();
                    if ("exit".equals(line)) {
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
	}

	private void runWithTray() {
		final SystemTray tray = SystemTray.getSystemTray();
		Image image = Toolkit.getDefaultToolkit().getImage("./res/code2html.png");
		PopupMenu popup = new PopupMenu();
		trayIcon = new TrayIcon(image, "Code2Html", popup);

		/*
		 * item = new MenuItem("Info"); item.addActionListener(new
		 * ShowMessageListener(trayIcon, "Info Title", "Info",
		 * TrayIcon.MessageType.INFO)); popup.add(item);
		 */
		

		MenuItem item = new MenuItem("Exit");
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tray.remove(trayIcon);
				exit();
			}
		});
		popup.add(item);

		try {
			tray.add(trayIcon);
		} catch (AWTException e) {
			System.err.println("Can't add to tray");
		}
	}

	// ----------------------------------------------------------

	@Override
	public void lostOwnership(Clipboard clipboard, Transferable contents) {
		// do nothing
	}
	
	private void init() {

        try {
            System.out.println("Loading properties from ./props/code2html.properties ...");
            loadProperties();
        } catch (Exception e) {
            System.out.println("Failed to load properties from ./props/code2html.properties");
            e.printStackTrace();
            System.out.println("Will use default properties");
        }
        

        JIntellitype.setLibraryLocation("./lib/JIntellitype.dll");
        JIntellitype jinstance = JIntellitype.getInstance();
        if (jinstance == null) {
			throw new RuntimeException("Failed to initialize JIntellitype.dll");
        }
        jinstance.addHotKeyListener(this);
		System.out.println("Register key listeners:");
		for (int i = 0; i < codeTypes.size(); i++) {
			char ch = String.valueOf(i + 1).charAt(0);
			jinstance.registerHotKey(i + 1, JIntellitype.MOD_CONTROL + JIntellitype.MOD_SHIFT, ch);
			System.out.println("      key Ctrl+Shift+" + ch + " registered for " + codeTypes.get(i).name());
		}

		infoMessageVisualizer.showInfoMessage("Code2Html is ready!");
    }
    
    private void loadProperties() throws Exception {

		// TODO:

        Properties props = new Properties();
        props.load(new FileInputStream(new File("./props/code2html.properties")));

		String keyHandler1 = props.getProperty("key.handler.1", "CSS");
		String keyHandler2 = props.getProperty("key.handler.2", "HTML");

		codeTypes.add(CodeType.getByName(keyHandler1, CodeType.CSS));
		codeTypes.add(CodeType.getByName(keyHandler2, CodeType.HTML));

		String themeCSS = props.getProperty("theme.CSS", "default");
		String themeHTML = props.getProperty("theme.HTML", "dark");

		Theme themeCss = Theme.getByName(themeCSS, Theme.DEFAULT);
		Theme themeHtml = Theme.getByName(themeHTML, Theme.DARK);

		System.out.println("Register themes:");
		
		Code2HtmlFactory.setTheme(CodeType.CSS, themeCss);
		System.out.println("      for " + CodeType.CSS.name() + " registered theme " + themeCss.name());
		
		Code2HtmlFactory.setTheme(CodeType.HTML, themeHtml);
		System.out.println("      for " + CodeType.HTML.name() + " registered theme " + themeHtml.name());
				
    }

    @Override
    public void onHotKey(int identifier) {
    	if(identifier < 0 || identifier > codeTypes.size()) {
    		return;
    	}
		CodeType codeType = codeTypes.get(identifier - 1);
		try {
			checkClipboard(codeType);
		} catch (Exception e) {
			infoMessageVisualizer.showInfoMessage("Failed to parse clipboard");
		}
    }

	private void checkClipboard(CodeType codeType) throws Exception {
        String inputText = getClipboardContents();
        if (Utils.isEmpty(inputText)) {
        	infoMessageVisualizer.showInfoMessage("Nothing interestingfound in clipboard.");
            return;
        }

		System.out.println("In clipboard " + codeType.name() + ":");
        System.out.println(inputText);
        
        IHtmlFormatter formatter = Code2HtmlFactory.getHtmlFormatter(codeType);
        IHtmlTheme theme = Code2HtmlFactory.getHtmlTheme(codeType);

        System.out.println("Formating input with theme: " + theme.getTheme().name());
        BufferedReader reader = new BufferedReader(new StringReader(inputText));
        String formattedText = formatter.format(reader, theme);
        
        setClipboardContents(formattedText);
        infoMessageVisualizer.showInfoMessage("Formatted input copied to clipboard!");
        System.out.println(formattedText);
    }

    public void setClipboardContents(String value) throws Exception {
        StringSelection stringSelection = new StringSelection(value);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, this);
    }

    public String getClipboardContents() throws Exception {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable contents = clipboard.getContents(null);
        boolean hasTransferableText = (contents != null) && contents.isDataFlavorSupported(DataFlavor.stringFlavor);
        if (hasTransferableText) {
            return (String) contents.getTransferData(DataFlavor.stringFlavor);
        } else {
            return null;
        }
    }

	// ------------------------------------------------------------------------------

	static class ShowMessageListener implements ActionListener {
		TrayIcon trayIcon;
		String title;
		String message;
		TrayIcon.MessageType messageType;

		ShowMessageListener(TrayIcon trayIcon, String title, String message, TrayIcon.MessageType messageType) {
			this.trayIcon = trayIcon;
			this.title = title;
			this.message = message;
			this.messageType = messageType;
		}

		public void actionPerformed(ActionEvent e) {
			trayIcon.displayMessage(title, message, messageType);
		}
	}

}
