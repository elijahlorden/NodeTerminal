package com.eli.networkterminal.objects;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

@SuppressWarnings("serial")
public class FTextField extends JTextField {

	public String promptText;
	public boolean isFocused;
	
	public FTextField(String unfocusPrompt){
		super(unfocusPrompt);
		isFocused = false;
		promptText = unfocusPrompt;
		addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent arg0) {
				isFocused = true;
				if (getText().equals(unfocusPrompt)){
					setText("");
				}
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				isFocused = false;
				if (getText().isEmpty()){
					setText(unfocusPrompt);
				}
			}
			
			
		});
		
		
	}
	
	
	
	
	
}
