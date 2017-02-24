package it.vige.realtime.messaging;

import static java.util.logging.Logger.getLogger;
import static org.jboss.shrinkwrap.api.ShrinkWrap.create;

import java.util.logging.Logger;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;

public class MessageTestCase {

	private static final Logger logger = getLogger(MessageTestCase.class.getName());

	@Deployment
	public static JavaArchive createEJBDeployment() {
		final JavaArchive jar = create(JavaArchive.class, "message.jar");
		return jar;
	}

	@Test
	public void testSendMessage() {
		logger.info("Start send message test");
	}
}
