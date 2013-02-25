import java.util.Date;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class ActiveMQTest {
	JmsTemplate jmsTemplate;

	@Before
	public void setUp() throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"activemq.xml");
		this.jmsTemplate = context.getBean(JmsTemplate.class);
	}

	@Test
	public void testSendMessages() throws Exception {

		int numberOfMessages = 10;


		for (int i = 0; i < numberOfMessages; ++i) {
			final int c = i;
			final StringBuilder payload = new StringBuilder();

			payload.append("Message [").append(i).append("] sent at: ")
					.append(new Date());

			jmsTemplate.send(new MessageCreator() {

				public Message createMessage(Session session)
						throws JMSException {

					TextMessage message = session.createTextMessage(payload.toString());

					message.setIntProperty("messageCount", c);

					return message;

				}

			});

		}

	}

}
