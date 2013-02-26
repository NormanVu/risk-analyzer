import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ActiveMQConsumerTest {

	@Test
	public void testReceive() throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext("activemq-consumer.xml");
		Thread.sleep(1000); // so i can consume a bunch of messages!
	}

}
