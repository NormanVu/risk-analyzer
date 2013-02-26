import java.util.ArrayList;
import java.util.List;

import org.apache.activemq.broker.BrokerPlugin;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.security.AuthenticationUser;
import org.apache.activemq.security.SimpleAuthenticationPlugin;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@Ignore
public class ActiveMQBrokerTest {
	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	@Test
	public void startProgrammatically() throws Exception {
		BrokerService broker = new BrokerService();
		broker.setBrokerName("myBroker");
		broker.setDataDirectoryFile(temporaryFolder.newFolder("data"));
		SimpleAuthenticationPlugin authentication = new SimpleAuthenticationPlugin();
		List<AuthenticationUser> users = new ArrayList<AuthenticationUser>();
		users.add(new AuthenticationUser("admin", "password",
				"admins,publishers,consumers"));
		broker.setPlugins(new BrokerPlugin[] { authentication });
		broker.addConnector("tcp://localhost:61616");
		broker.start();
		System.out.println();
		System.out.println("Press any key to stop the broker");
		System.out.println();
		System.in.read();
	}

	@Test
	public void startWithSpringConfig() throws Exception {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"activemq-broker.xml");
		BrokerService service = ctx.getBean(BrokerService.class);
		service.start();
		System.out.println();
		System.out.println("Press any key to stop the broker");
		System.out.println();
		System.in.read();
	}

}
