package com.eli.networkterminal.objects;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.eli.networkterminal.main.Constants;

public class TerminalWindow {

	public JFrame window;
	public FTextField inputBox;
	public JTextPane console;
	public JScrollPane scrollPane;
	
	public StyledDocument document;
	
	public ArrayList<String> printQueue;
	public boolean processingPrintQueue;
	
	public TerminalWindow(String name, String prompt) {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		printQueue = new ArrayList<String>();
		processingPrintQueue = false;
		
		window = new JFrame();
		inputBox = new FTextField(prompt);
		console = new JTextPane();
		scrollPane = new JScrollPane(console);
		
		window.add(inputBox, BorderLayout.SOUTH);
		window.add(scrollPane, BorderLayout.CENTER);
		
		inputBox.setFont(new Font("Courrier New", Font.PLAIN, 13));
		inputBox.setEditable(true);
		inputBox.setOpaque(false);
		inputBox.setCaretColor(Color.WHITE);
		inputBox.setForeground(Color.WHITE);
		inputBox.setBorder(null);
		
		console.setEditable(false);
		console.setFont(new Font("Courrier New", Font.PLAIN, 13));
		console.setOpaque(false);
		console.setContentType("text/html");
		document = console.getStyledDocument();
		
		
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setBorder(null);
		
		
		window.getContentPane().setBackground(new Color(0,0,0));
		window.setTitle(name);
		window.setLocationRelativeTo(null);
		window.setSize(660, 350);
		window.setVisible(true);
	}
	
	public void setFont(Font font) {
		console.setFont(font);
		inputBox.setFont(font);
	}
	
	public Font getFont() {
		return console.getFont();
	}
	
	public void scrollBottom() {
		console.setCaretPosition(console.getDocument().getLength());
	}
	
	public void scrollTop() {
		console.setCaretPosition(0);
	}
	
	public void print(String s, boolean trace) {
		
		if (trace) {
			Throwable t = new Throwable();
			StackTraceElement[] elements = t.getStackTrace();
			String caller = elements[0].getClassName();
			s = caller + " --> " + s;
		}
		
		printQueue.add(s);
		processQueue();
	}
	
	public void print(String s) {
		print(s, false);
	}
	
	public void println(String s, boolean trace) {
		print(s + "\n", trace);
	}
	
	public void println(String s) {
		print(s + "\n", false);
	}
	
	public void processQueue() {
		if (processingPrintQueue == true) return;
		processingPrintQueue = true;
		Style style = console.addStyle("Style", null);
		StyleConstants.setForeground(style, Color.WHITE);
		//StyleConstants.setBackground(style, Color.white);
		
		while(printQueue.size() > 0) {
			String ps = printQueue.get(0);
			printQueue.remove(0);
			processPrintString(ps, style);
		}
		
		processingPrintQueue = false;
	}
	
	public void processPrintString(String s, Style style) {
		if (s == "#$!CLEAR") {
			try {
				document.remove(0, document.getLength());
			} catch (Exception e) {}
			return;
		}
		int currPos = 0;
		while(s.length() > 0) {
			String token = s.substring(0, 1);
			s = s.substring(1);
			currPos++;
			if (token.equals("\\") && s.substring(0, 1).equals(Constants.openTag)) { //allow escaping of the open tag symbol so it can be used in strings.
				s = s.substring(1);
				try {
					document.insertString(document.getLength(), Constants.openTag, style);
				} catch (Exception e) {}
				continue;
			} else if (token.equals(Constants.openTag)) { //opentag opens the text-attrib tag
				while (!token.endsWith(Constants.closeTag) && s.length() > 0){ //get the rest of the tag.  If the tag is not closed, the rest of the string will not be printed.
					token+= s.substring(0, 1);
					s = s.substring(1);
					currPos++;
				}
				processTag(token, style);
			} else {
				try {
					document.insertString(document.getLength(), token, style);
				} catch (Exception e) {}
			}
		}
		scrollBottom();
	}
	
	public void processTag(String tag, Style style) {
		tag = tag.substring(1,tag.length()-1).toLowerCase();
		String[] tagArgs = tag.split(" ");
		if (tagArgs.length < 1) return;
		if (tagArgs[0].equals("color") || tagArgs[0].equals("c")) { // color tag <color>
			colorTag(style, tagArgs);
		} else if (tagArgs[0].equals("uncolor") || tagArgs[0].equals("/c")) { // uncolor tag <uncolor> </color>
			StyleConstants.setForeground(style, Color.WHITE);
		} else if (tagArgs[0].equals("bold") || tagArgs[0].equals("b")) { // bold tag <bold> <b>
			StyleConstants.setBold(style, true);
		} else if (tagArgs[0].equals("unbold") || tagArgs[0].equals("/b")) { // unbold tag <unbold> </b>
			StyleConstants.setBold(style, false);
		} else if (tagArgs[0].equals("italics") || tagArgs[0].equals("i")) { // italics tag <italics> <i>
			StyleConstants.setItalic(style, true);
		} else if (tagArgs[0].equals("unitalics") || tagArgs[0].equals("/i")) { // unitalics tag <unitalics> </i>
			StyleConstants.setItalic(style, false);
		} else if (tagArgs[0].equals("background") || tagArgs[0].equals("bg")) { // background-color tag <italics> <i>
			backgroundColorTag(style, tagArgs);
		} else if (tagArgs[0].equals("unbackground") || tagArgs[0].equals("/bg")) { // un-background-color  tag <unitalics> </i>
			StyleConstants.setBackground(style, Color.BLACK);
		}
		
		
	}
	
	public void colorTag(Style style, String[] args) {
		if (args.length < 2) return;
		if (Constants.colorMap.containsKey(args[1].toUpperCase())){
			StyleConstants.setForeground(style, Constants.colorMap.get(args[1].toUpperCase()));
		} else {
			try {
				StyleConstants.setForeground(style, Color.decode(args[1]));
			} catch (Exception e) {}
		}
	}
	
	public void backgroundColorTag(Style style, String[] args) {
		if (args.length < 2) return;
		if (Constants.colorMap.containsKey(args[1].toUpperCase())){
			StyleConstants.setBackground(style, Constants.colorMap.get(args[1].toUpperCase()));
		} else {
			try {
				StyleConstants.setBackground(style, Color.decode(args[1]));
			} catch (Exception e) {}
		}
	}
	
	
	
	
	
	public void clear() {
		printQueue.add("#$!CLEAR");
		processQueue();
	}
	
	
	
	
	
}
