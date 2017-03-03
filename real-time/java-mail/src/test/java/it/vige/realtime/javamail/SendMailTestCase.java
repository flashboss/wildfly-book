package it.vige.realtime.javamail;

import static java.util.logging.Logger.getLogger;
import static org.jboss.shrinkwrap.api.ShrinkWrap.create;

import java.util.logging.Logger;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class SendMailTestCase {

	private static final Logger logger = getLogger(SendMailTestCase.class.getName());

	@Deployment
	public static JavaArchive createEJBDeployment() {
		final JavaArchive jar = create(JavaArchive.class, "sendmail.jar");
		jar.addPackage(SendMail.class.getPackage());
		return jar;
	}

	@Test
	@RunAsClient
	public void send() throws AddressException, MessagingException {
		SendMail sendMail = new SendMail();
		sendMail.completeClientSend();
		logger.info("\n\n ===> Your Java Program has just sent an Email successfully. Check your email..");
	}
}
