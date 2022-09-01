package com.rohan.jms.hm.clinincals;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.MapMessage;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import com.rohan.jms.hm.model.Patient;

public class ClinincalsApp {

	public static void main(String[] args) throws NamingException, JMSException {

		InitialContext initialContext = new InitialContext();
		Queue requestQueue = (Queue) initialContext.lookup("queue/requestQueue");
		Queue replyQueue = (Queue) initialContext.lookup("queue/replyQueue");

		try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmsContext = cf.createContext();) {

			JMSProducer producer = jmsContext.createProducer();

			ObjectMessage objMessage = jmsContext.createObjectMessage();
			Patient patient = new Patient();
			patient.setId(123);
			patient.setName("Bob");
			patient.setInsuranceProvider("Blue Cross Blue Shield");
			patient.setCopay(100d);
			patient.setAmountToBePayed(500d);
			objMessage.setObject(patient);

			for (int i = 1; i <= 10; i++) {
				producer.send(requestQueue, objMessage);
			}

//			JMSConsumer consumer = jmsContext.createConsumer(replyQueue);
//			MapMessage replyMessage = (MapMessage) consumer.receive(30000);
//			System.out.println("Patient Eligibility: "+replyMessage.getBoolean("eligible"));

		}
		;
	}

}
