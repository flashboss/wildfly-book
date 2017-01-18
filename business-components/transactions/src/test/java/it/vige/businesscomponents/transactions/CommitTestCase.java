package it.vige.businesscomponents.transactions;

import static java.util.logging.Logger.getLogger;
import static org.jboss.shrinkwrap.api.ShrinkWrap.create;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.asset.FileAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class CommitTestCase {

	private static final Logger logger = getLogger(CommitTestCase.class.getName());

	@EJB
	private Bank bank;

	@PersistenceContext
	private EntityManager entityManager;

	@Deployment
	public static JavaArchive createEJBDeployment() {
		final JavaArchive jar = create(JavaArchive.class, "commit-test.jar");
		jar.addPackage(Account.class.getPackage());
		jar.addAsManifestResource(new FileAsset(new File("src/test/resources/META-INF/persistence-test.xml")),
				"persistence.xml");
		return jar;
	}

	@Test
	public void testCMT() throws Exception {
		logger.info("start CMT test");
		bank.move(123, 345, 566.9);
		Account from = entityManager.find(Account.class, 123);
		Account to = entityManager.find(Account.class, 345);
		assertEquals("the amont is reduced", 4988.97, from.getCredit(), 0.0);
		assertEquals("the amont is increased", 3122.77, to.getCredit(), 0.0);
	}
}
