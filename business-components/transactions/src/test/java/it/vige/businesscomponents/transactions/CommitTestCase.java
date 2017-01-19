package it.vige.businesscomponents.transactions;

import static java.util.logging.Logger.getLogger;
import static org.jboss.shrinkwrap.api.ShrinkWrap.create;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

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
	private PoorBank poorBank;

	@EJB
	private RichBank richBank;

	@PersistenceContext
	private EntityManager entityManager;

	@Inject
	private UserTransaction userTransaction;

	@Deployment
	public static JavaArchive createEJBDeployment() {
		final JavaArchive jar = create(JavaArchive.class, "commit-test.jar");
		jar.addPackage(Account.class.getPackage());
		jar.addAsManifestResource(new FileAsset(new File("src/test/resources/META-INF/persistence-test.xml")),
				"persistence.xml");
		jar.addAsResource(new FileAsset(new File("src/test/resources/store.import.sql")), "store.import.sql");
		return jar;
	}

	@Test
	public void testNoTransactionOK() throws Exception {
		logger.info("start CMT test");
		poorBank.move(123, 345, 566.9);
		Account from = entityManager.find(Account.class, 123);
		Account to = entityManager.find(Account.class, 345);
		assertEquals("the amont is reduced", 4988.97, from.getCredit(), 0.0);
		assertEquals("the amont is increased", 3122.77, to.getCredit(), 0.0);
	}

	@Test
	public void testNoTransactionFail() {
		logger.info("start CMT test");
		try {
			poorBank.move(1231, 3451, -20);
			fail("The add method generates an Exception");
		} catch (Exception ex) {
			Account from = entityManager.find(Account.class, 1231);
			Account to = entityManager.find(Account.class, 3451);
			assertEquals("the amont is reduced", 5575.87, from.getCredit(), 0.0);
			assertEquals("the amont is not encreased", 2555.87, to.getCredit(), 0.0);
		}
	}

	@Test
	public void testTransactionFail() {
		logger.info("start CMT test");
		try {
			userTransaction.begin();
			poorBank.move(2231, 4451, -20);
			fail("The add method generates an Exception");
			userTransaction.commit();
		} catch (Exception ex) {
			try {
				userTransaction.rollback();
				Account from = entityManager.find(Account.class, 2231);
				Account to = entityManager.find(Account.class, 4451);
				assertEquals("the amont is reduced", 5555.87, from.getCredit(), 0.0);
				assertEquals("the amont is not encreased", 2555.87, to.getCredit(), 0.0);
			} catch (IllegalStateException | SecurityException | SystemException e) {
				fail("not good");
			}
		}
	}

	@Test
	public void testTransactionalEJBFail() {
		logger.info("start CMT test");
		try {
			richBank.move(3231, 5451, -20);
			fail("The add method generates an Exception");
		} catch (Exception ex) {
			Account from = entityManager.find(Account.class, 3231);
			Account to = entityManager.find(Account.class, 5451);
			assertEquals("the amont is reduced", 5555.87, from.getCredit(), 0.0);
			assertEquals("the amont is not encreased", 2555.87, to.getCredit(), 0.0);

		}
	}
}
