package com.ss.code2html.console;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Properties;

import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.JIntellitype;
import com.ss.code2html.engine.Code2HtmlFactory;
import com.ss.code2html.engine.CodeType;
import com.ss.code2html.engine.IHtmlFormatter;
import com.ss.code2html.engine.IHtmlTheme;
import com.ss.code2html.engine.Theme;
import com.ss.code2html.utils.Utils;

public class Code2Html implements HotkeyListener, ClipboardOwner {

    public static void main(String[] args) {
        System.out.println("Code2Html is application to convert code to html.");
        System.out.println("Copy code to clipboard, press Ctrl+Shift+C, paste html to your destination.");
        System.out.println("Type 'exit', to exit.");
        new Code2Html();
        System.out.println("Buy!");
    }

    public Code2Html() {
        try {
            start();
        } finally {
            stop();
        }
    }

    @Override
    public void lostOwnership(Clipboard clipboard, Transferable contents) {
        // do nothing
    }

    private void stop() {
        JIntellitype.getInstance().cleanUp();
        System.exit(0);
    }

    public void start() {
        init();

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
            throw new RuntimeException("Failed to initialize code2html");
        }
        jinstance.addHotKeyListener(this);
        jinstance.registerHotKey(1, JIntellitype.MOD_CONTROL + JIntellitype.MOD_SHIFT, (int) 'C');
    }
    
    private void loadProperties() throws Exception {
        Properties props = new Properties();
        props.load(new FileInputStream(new File("./props/code2html.properties")));

        String themeName = props.getProperty("theme", "default");
        Theme theme = Theme.getByName(themeName);
        if (theme == null) {
            theme = Theme.DEFAULT;
        }
        Code2HtmlFactory.setCurrentTheme(theme);
        System.out.println("Default theme is set to " + theme.name());
    }

    @Override
    public void onHotKey(int identifier) {
        if (identifier == 1) {
            try {
                System.out.println("Checking clipboard...");
                checkClipboard();
            } catch (Exception e) {
                System.out.println("Failed to parse clipboard");
            }
        }
    }

    private void checkClipboard() throws Exception {
        String inputText = getClipboardContents();
        if (Utils.isEmpty(inputText)) {
            System.out.println("Nothing interestingfound in clipboard.");
            return;
        }

        System.out.println("In clipboard: ");
        System.out.println(inputText);
        
        CodeType codeType = Code2HtmlFactory.getCodeRegognizer().recognize(inputText);
        System.out.println("Text recognized as: " + codeType.name());
        
        IHtmlFormatter formatter = Code2HtmlFactory.getHtmlFormatter(codeType);
        IHtmlTheme theme = Code2HtmlFactory.getHtmlTheme(codeType);

        System.out.println("Formating input with theme: " + theme.getTheme().name());
        BufferedReader reader = new BufferedReader(new StringReader(inputText));
        String formattedText = formatter.format(reader, theme);
        
        setClipboardContents(formattedText);
        System.out.println("Formatted input copied to clipboard: ");
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

}
