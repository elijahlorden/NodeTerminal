package com.eli.networkterminal.objects;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.text.BadLocationException;

import com.eli.networkterminal.command.Commands;
import com.eli.networkterminal.main.Constants;
import com.eli.networkterminal.main.Main;
import com.eli.networkterminal.tools.Formatting;

public class MainTerminalWindow extends TerminalWindow {
	
	public ArrayList<String> history;
	public int historyIndex = -1;
	
	public MainTerminalWindow(String name) {
		super(name, ">>");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		history = new ArrayList<String>();
		
		
		inputBox.addActionListener(new ActionListener(){
			boolean debounce = false;
			@Override
			public void actionPerformed(ActionEvent e) {
				if (debounce) return;
				debounce = true;
				String inputText = inputBox.getText();
				if (inputText != ">>" && inputText.replaceAll("\\s+", "").length() > 0){
					String outputText = Formatting.removeTags(inputText);
					String formattedOutput = Formatting.formatCommandColors(outputText);
					try {
						if (!document.getText(0, document.getLength()).endsWith("\n")) {
							document.insertString(document.getLength(), "\n", null);
						}
					} catch (Exception ex) {}
					
					println(Formatting.tag("Color", "Yellow") + "local " + Formatting.tag("/c") + ">> " + Formatting.tag("Color", "Cyan") + formattedOutput, false);
					addHistory(inputText);
					inputBox.selectAll();
					Commands.processLocalCommand(inputText, Main.localTerminal);
				}
				
				debounce = false;
			}
			
		});
		
		inputBox.addKeyListener(new KeyListener(){
	
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					if (inputBox.isFocused) {
						historyIndex++;
						historyIndex = Math.min(historyIndex, history.size() - 1);
						if (history.get(history.size() - historyIndex - 1).equals(inputBox.getText())) {
							historyIndex++;
							historyIndex = Math.min(historyIndex, history.size() - 1);
						}
					}
					inputBox.setText(history.get(history.size() - historyIndex - 1));
					inputBox.selectAll();
				} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					if (inputBox.isFocused) {
						historyIndex--;
						historyIndex = Math.max(historyIndex, 0);
					}
					inputBox.setText(history.get(history.size() - historyIndex - 1));
					inputBox.selectAll();
				} else {
					historyIndex = -1;
				}
			}
	
			@Override
			public void keyReleased(KeyEvent e) {
				
			}
	
			@Override
			public void keyTyped(KeyEvent e) {
				
			}
			
		});
		
	
	}
	
	public void addHistory(String s){
		if (history.size() > 0) {
			if (!s.equals(history.get(history.size()-1))) {
				history.add(s);
			}
		} else {
			history.add(s);
		}
		if (history.size() > 50) {
			history.remove(1);
		}
	}
	
	
	
	
}
