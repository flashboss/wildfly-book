package it.vige.realtime.messaging;

import static java.util.logging.Logger.getLogger;
import static org.jboss.as.test.integration.common.jms.JMSOperationsProvider.getInstance;
import static org.jboss.shrinkwrap.api.ShrinkWrap.create;

import java.util.logging.Logger;

import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.as.arquillian.api.ServerSetup;
import org.jboss.as.arquillian.api.ServerSetupTask;
import org.jboss.as.arquillian.container.ManagementClient;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import it.vige.realtime.messaging.MessageTestCase.MessagingResourcesSetupTask;
import it.vige.realtime.messaging.clients.MessageSender;

@RunWith(Arquillian.class)
@ServerSetup(MessagingResourcesSetupTask.class)
public class MessageTestCase {

	private static final Logger logger = getLogger(MessageTestCase.class.getName());

	private static final String QUEUE_NAME = "gps_coordinates";
	public static final String QUEUE_LOOKUP = "java:/jms/queue/GPS";

	@EJB
	private MessageSender messageSender;

	static class MessagingResourcesSetupTask implements ServerSetupTask {

		@Override
		public void setup(ManagementClient managementClient, String containerId) throws Exception {
			getInstance(managementClient.getControllerClient()).createJmsQueue(QUEUE_NAME, QUEUE_LOOKUP);
		}

		@Override
		public void tearDown(ManagementClient managementClient, String containerId) throws Exception {
			getInstance(managementClient.getControllerClient()).removeJmsQueue(QUEUE_NAME);
		}
	}

	@Deployment
	public static JavaArchive createEJBDeployment() {
		final JavaArchive jar = create(JavaArchive.class, "message.jar");
		jar.addPackage(MessageSender.class.getPackage());
		return jar;
	}

	@Test
	public void testSendMessage() {
		logger.info("Start send message test");
		messageSender.sendMessage("hello!");
	}
}
