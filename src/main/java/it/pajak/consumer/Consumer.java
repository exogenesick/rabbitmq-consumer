package it.pajak.consumer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

public class Consumer extends JFrame 
{
    public static void main(String[] args) 
    {
        try {
            new Consumer();
        } catch (HeadlessException ex) {
            Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public Consumer() throws HeadlessException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException 
    {    
        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            if ("com.sun.java.swing.plaf.gtk.GTKLookAndFeel".equals(info.getClassName())) {   
               javax.swing.UIManager.setLookAndFeel(info.getClassName());
               break;
             } 
        }
        
        configureInputs();
        configureMainWindow();
    }
    
    private void configureMainWindow() 
    {
        setTitle("MQ Consumer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }
    
    private void configureInputs() 
    {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        
        JPanel northPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        
        JLabel hostLabel = new JLabel("RabbitMQ address:");
        hostLabel.setHorizontalAlignment(JLabel.RIGHT);
        northPanel.add(hostLabel);
        
        JTextField hostField = new JTextField("127.0.0.1");
        JTextField queueNameField = new JTextField();
        JList list = new JList();
        JComboBox load = new JComboBox(LOAD_OPTIONS);
        
        JButton startConsumeButton = new JButton("Start consuming");
        startConsumeButton.setPreferredSize(new Dimension(300, 100));
        startConsumeButton.setHorizontalAlignment(JButton.CENTER);
        startConsumeButton.setEnabled(false);
        startConsumeButton.setForeground(Color.green);
        
        startConsumeButton.addActionListener(new ButtonListener(hostField, queueNameField, load, list));
        
        hostField.getDocument().addDocumentListener(new TextInputEventListener(startConsumeButton));
        northPanel.add(hostField);

        JLabel queueNameLabel = new JLabel("Queue name:");
        queueNameLabel.setHorizontalAlignment(JLabel.RIGHT);
        northPanel.add(queueNameLabel);
        
        queueNameField.getDocument().addDocumentListener(new TextInputEventListener(startConsumeButton));
        northPanel.add(queueNameField);
        
        JLabel loadLabel = new JLabel("Machine load:");
        loadLabel.setHorizontalAlignment(JLabel.RIGHT);
        northPanel.add(loadLabel);

        load.setSelectedIndex(0);
        northPanel.add(load);
        
        northPanel.setBorder(new EmptyBorder(10,10, 10, 10));

        panel.add(northPanel);
        
        JPanel middlePanel = new JPanel(new GridLayout(0, 1, 10, 10));
        middlePanel.setBorder(new EmptyBorder(0, 10, 0, 10));
        middlePanel.add(startConsumeButton);
        panel.add(middlePanel);
		
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(-1);
        list.setSelectionMode(DefaultListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        JScrollPane scroll = new JScrollPane(list);
        scroll.setPreferredSize(new Dimension(100, 200));
        scroll.setBorder(new EmptyBorder(10,10, 10, 10));
        panel.add(scroll);
        
        add(panel);
    }

    private static String[] LOAD_OPTIONS = { "0 (not loaded)", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };
}
