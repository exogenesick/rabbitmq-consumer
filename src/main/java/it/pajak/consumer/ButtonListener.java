package it.pajak.consumer;

import it.pajak.consumer.ConsumeSimpleTask;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JTextField;

public class ButtonListener implements ActionListener 
{
    private JTextField hostField;    
    private JTextField queueNameField; 
    private JList logList;
    private JComboBox loadField;
    private Task currentTask;

    public ButtonListener(JTextField host, JTextField queueName, JComboBox load, JList list) 
    {
        hostField = host;
        queueNameField = queueName;
        loadField = load;
        logList = list;
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        startNewWorker((JButton) e.getSource());
    }
    
    private void startNewWorker(JButton button)
    {
        try {
            currentTask = chooseWorker();
            new Thread(currentTask).start();
            
            button.setEnabled(false);
            hostField.setEnabled(false);
            queueNameField.setEnabled(false);
            loadField.setEnabled(false);
        } catch (IOException ex) {}
    }
    
    private Task chooseWorker() throws IOException
    {
        return new ConsumeSimpleTask(hostField.getText(), queueNameField.getText(), loadField.getSelectedIndex(), logList);
    }
}
