import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class MyMessageListener implements MessageListener {

	@Override
	public void onMessage(Message message) {
		try {
			TextMessage msg = (TextMessage) message;
			System.out.println("Consumed message: " + msg.getText());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
