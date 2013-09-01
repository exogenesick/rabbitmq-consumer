package it.pajak.consumer;

import javax.swing.JButton;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

public class TextInputEventListener implements DocumentListener 
{
    private JButton button;
    
    public TextInputEventListener(JButton b) 
    {
        button = b;
    }
    
    @Override
    public void insertUpdate(DocumentEvent e) 
    {
        try {
            String value = e.getDocument().getText(0, e.getDocument().getLength());
            switchButton(value);
        } catch (BadLocationException ex) {
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) 
    {
        try {
            String value = e.getDocument().getText(0, e.getDocument().getLength());
            switchButton(value);
        } catch (BadLocationException ex) {
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) 
    {
        try {
            String value = e.getDocument().getText(0, e.getDocument().getLength());
            switchButton(value);
        } catch (BadLocationException ex) {
        }
    }

    private void switchButton(String value) 
    {
        if (value.equalsIgnoreCase("")) {
            button.setEnabled(false);
        } else {
            button.setEnabled(true);
        }
    }
}
