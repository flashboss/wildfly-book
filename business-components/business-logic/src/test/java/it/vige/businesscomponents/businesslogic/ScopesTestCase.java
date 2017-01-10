package it.vige.businesscomponents.businesslogic;

import static java.util.logging.Logger.getLogger;
import static org.jboss.shrinkwrap.api.ShrinkWrap.create;
import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class ScopesTestCase {

	private static final Logger logger = getLogger(ScopesTestCase.class.getName());

	@Inject
	private MyPosts myPosts;

	@Inject
	private MyHardPosts myHardPosts;

	@Deployment
	public static JavaArchive createJavaDeployment() {
		final JavaArchive jar = create(JavaArchive.class, "scopes-test.jar");
		jar.addPackage(MyPosts.class.getPackage());
		return jar;
	}

	@Test
	public void testStateful() {
		logger.info("Start stateful test");
		myPosts.getPostsByDay(0);
		List<Post> posts = myPosts.getLastRequestedPosts();
		assertEquals(0, posts.size());
		myHardPosts.getPostsByDay(0);
		List<Post> hardPosts = myHardPosts.getLastRequestedPosts();
		assertEquals(2, hardPosts.size());
	}
}
