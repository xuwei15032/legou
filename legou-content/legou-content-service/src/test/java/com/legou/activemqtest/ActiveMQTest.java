package com.legou.activemqtest;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

public class ActiveMQTest {

	// 生产者 发消息
	@Test
	public void testQueueProducerHaotian() throws Exception {
		// queue类型的消息又称为点对点
		// tcp http
		// 创建连接工厂 tcp协议的类型 mq所在的机器ip 61616 mq的通信端口
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.128:61616");

		// 通过连接工厂可以获取连接
		Connection connection = connectionFactory.createConnection();

		// 启用连接
		connection.start();

		// 通过connection 创建session
		// 第一个参数：true false true 如果是true 表示创建出来的session支持事务， 如果是true那么第二个参数就不起作用

		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 使用session创建query
		Queue queue = session.createQueue("zhaotiantianqueue1");

		// 在创建producer 需要传递producer发送消息的目的地
		MessageProducer zhaohaotian = session.createProducer(queue);

		// 发消息之前一定先创建消息（发消息就是传递数据）
		TextMessage text = session.createTextMessage("丽丽 我真的爱你");

		// 使用生产者的send方法发送消息
		zhaohaotian.send(text);

		// 关闭资源
		zhaohaotian.close();
		session.close();
		connection.close();

	}

	// 消费者 接受消息
	@Test
	public void testQueueConsumerLili() throws Exception {

		// 创建ActiveMQ的连接工厂
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.128:61616");

		// 使用activemq连接工厂创建连接
		Connection connection = connectionFactory.createConnection();

		// 开启connection

		connection.start();

		// 创建session
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		// 通过session创建queue createQueue需要传递queue的名字如果该名字存在则不创建
		Queue queue = session.createQueue("zhaotiantianqueue1");

		// 创建消费者
		MessageConsumer lili = session.createConsumer(queue);

		// lili需要开始接受浩天的消息
		// 创建匿名类 new 接口

		lili.setMessageListener(new MessageListener() {

			@Override
			public void onMessage(Message message) {

				TextMessage textMessage = (TextMessage) message;
				try {
					String string = textMessage.getText();
					System.out.println(string);
					//
					if (!string.equals("")) {
						System.out.println("天天我也爱你");
					}
				} catch (JMSException e) {
					e.printStackTrace();
				}

			}
		});

		// 阻塞线程
		System.in.read();
		lili.close();
		session.close();
		connection.close();

	}
	
	
	// 消费者 接受消息
		@Test
		public void testQueueConsumerHong() throws Exception {

			// 创建ActiveMQ的连接工厂
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.131:61616");

			// 使用activemq连接工厂创建连接
			Connection connection = connectionFactory.createConnection();

			// 开启connection

			connection.start();

			// 创建session
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			// 通过session创建queue createQueue需要传递queue的名字如果该名字存在则不创建
			Queue queue = session.createQueue("zhaotiantianqueue1");

			// 创建消费者
			MessageConsumer lili = session.createConsumer(queue);

			// lili需要开始接受浩天的消息
			// 创建匿名类 new 接口

			lili.setMessageListener(new MessageListener() {

				@Override
				public void onMessage(Message message) {

					TextMessage textMessage = (TextMessage) message;
					try {
						String string = textMessage.getText();
						System.out.println(string);
						//
						if (!string.equals("")) {
							System.out.println("天天我也爱你");
						}
					} catch (JMSException e) {
						e.printStackTrace();
					}

				}
			});

			// 阻塞线程
			System.in.read();
			lili.close();
			session.close();
			connection.close();

		}


}
