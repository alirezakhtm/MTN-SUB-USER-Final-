
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mtn.data.object.DataReceived;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author alirezakhtm
 */
public class Test {
    public static void main(String[] args) {
        DataReceived dataReceived = new DataReceived(
                "userId",
                "SpID",
                "productID",
                "serviceID",
                "serviceList",
                110,
                "updateTime",
                "updateDesc",
                "effectiveTime",
                "expiryTime",
                "extensionInfo.value.toString()",
                "result.value.toString()",
                "resultDescription.value");
        Gson gson = new GsonBuilder().create();
        String strJson = gson.toJson(dataReceived, DataReceived.class);
        // Connect to ActiveMQ and put data into Queue
        ActiveMQConnectionFactory connectionFactory = 
                new ActiveMQConnectionFactory("admin", "admin", "tcp://localhost:61616");
        Connection connection = null;
        try{
            connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue("DataSync-Queue");
            MessageProducer producer = session.createProducer(destination);
            TextMessage message = session.createTextMessage(strJson);
            producer.send(message);
            producer.close();
            session.close();
            System.out.println(strJson);
        }catch(JMSException e){
            System.err.println(e);
        }finally{
            if(connection != null){
                try{
                    connection.close();
                }catch(JMSException e){
                    System.err.println(e);
                }
            }
        }
    }
}
