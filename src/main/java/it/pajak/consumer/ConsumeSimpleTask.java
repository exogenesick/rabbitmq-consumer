package it.pajak.consumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;
import java.io.IOException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JList;

public class ConsumeSimpleTask extends Task
{
    private final static boolean QUEUE_DURABILITY = true;
    private final static boolean AUTO_ACKNOWLEDGMENT = false;
    private QueueingConsumer consumer;
    private Channel channel;
    private JList logsList;
    private Vector<String> logs;
    private int load;
    
    public ConsumeSimpleTask (String address, String queueName, int loadValue, JList list) throws IOException 
    {
        logsList = list;
        logs = new Vector<String>();
        load = loadValue;
        
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(address);
            
        Connection connection = factory.newConnection();
        
        channel = connection.createChannel();
        channel.queueDeclare(queueName, QUEUE_DURABILITY, false, false, null);
        channel.basicQos(1);
        
        consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName, AUTO_ACKNOWLEDGMENT, consumer);    
    }
    
    public void run() 
    {
        while (true) {
            try {
                Thread.sleep(load * 1000);
            } catch (InterruptedException ex) {
                System.exit(1);
            }
            
            QueueingConsumer.Delivery delivery;
            try {
                delivery = consumer.nextDelivery();

                String message = new String(delivery.getBody());
                logs.addElement(message);
                logsList.setListData(logs);
                logsList.ensureIndexIsVisible(logsList.getModel().getSize() - 1);
                
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }   catch (InterruptedException ex) {
                Logger.getLogger(ConsumeSimpleTask.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ShutdownSignalException ex) {
                Logger.getLogger(ConsumeSimpleTask.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ConsumeSimpleTask.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
