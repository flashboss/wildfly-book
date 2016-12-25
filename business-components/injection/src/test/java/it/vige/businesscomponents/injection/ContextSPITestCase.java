package it.vige.businesscomponents.injection;

import static java.util.logging.Logger.getLogger;
import static org.jboss.shrinkwrap.api.ShrinkWrap.create;
import static org.jboss.shrinkwrap.api.asset.EmptyAsset.INSTANCE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.logging.Logger;

import javax.enterprise.context.Conversation;
import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import it.vige.businesscomponents.injection.context.ConversationBean;

@RunWith(Arquillian.class)
public class ContextSPITestCase {

	private static final Logger logger = getLogger(ContextSPITestCase.class.getName());

	@Inject
	private ConversationBean conversationBean;

	@Deployment
	public static JavaArchive createWebDeployment() {
		final JavaArchive jar = create(JavaArchive.class, "context-spi-test.jar");
		jar.addPackage(ConversationBean.class.getPackage());
		jar.addAsManifestResource(INSTANCE, "beans.xml");
		return jar;
	}

	/**
	 * Tests simple conversation in a jar archive
	 */
	@Test
	public void testConversation() {
		logger.info("start conversation test");
		Conversation conversation = conversationBean.getConversation();
		assertEquals("deafult timeout is 600000 millis", 600000, conversation.getTimeout());
		assertEquals("when the conversation is not started it is transient", true, conversation.isTransient());
		assertNull("I have not started the conversation so the id will be null", conversation.getId());
		conversationBean.initConversation();
		assertEquals("verifiing the openeded conversation", "1", conversation.getId());
		assertEquals("when the conversation is started it is not transient", false, conversation.isTransient());
		conversationBean.endConversation();
		assertNull("when I close the conversation the id will be null", conversation.getId());
		assertEquals("when the conversation is ended it is transient again", true, conversation.isTransient());
		logger.info("end conversation test");
	}
}
