package com.legou.activemqtest;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class ActiveMQTestForSpring {

	@Test
	public void Test01() {

		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-mq.xml");

		ActiveMQConnectionFactory ac = (ActiveMQConnectionFactory) context.getBean("activeMQConnectionFactory");

		JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);

		Destination dest = (Destination) context.getBean("activeMQQueue");

		jmsTemplate.send(dest, new MessageCreator() {

			@Override
			public Message createMessage(Session session) throws JMSException {

				return session.createTextMessage("legou-spring-text-mesage");
			}
		});
	}

}
