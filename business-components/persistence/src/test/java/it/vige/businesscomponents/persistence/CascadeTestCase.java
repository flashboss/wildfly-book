package it.vige.businesscomponents.persistence;

import static it.vige.businesscomponents.persistence.Default.createJavaArchive;
import static java.util.logging.Logger.getLogger;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import it.vige.businesscomponents.persistence.cascade.Comment;
import it.vige.businesscomponents.persistence.cascade.Info;
import it.vige.businesscomponents.persistence.cascade.InfoDetails;

@RunWith(Arquillian.class)
public class CascadeTestCase {

	private static final Logger logger = getLogger(CascadeTestCase.class.getName());

	@PersistenceContext
	private EntityManager entityManager;

	@Inject
	private UserTransaction userTransaction;

	@Deployment
	public static JavaArchive createJavaDeployment() {
		return createJavaArchive("cascade-test.jar", Info.class.getPackage());
	}

	private void clean() {
		entityManager.createQuery("delete from Comment").executeUpdate();
		entityManager.createQuery("delete from InfoDetails").executeUpdate();
		entityManager.createQuery("delete from Info").executeUpdate();
	}

	@Test
	public void testPersistOneToOne() {
		logger.info("starting persist one to one cascade test");
		Info info = new Info();
		info.setName("Wildfly Book Info");
		InfoDetails details = new InfoDetails();
		info.addDetails(details);
		try {
			userTransaction.begin();
			clean();
			entityManager.persist(info);
			userTransaction.commit();
		} catch (NotSupportedException | SystemException | IllegalStateException | SecurityException
				| HeuristicMixedException | HeuristicRollbackException | RollbackException e) {
			fail();
		}
		@SuppressWarnings("unchecked")
		List<Info> infos = entityManager.createNativeQuery("select * from info").getResultList();
		assertEquals("the info table exists", 1, infos.size());
		@SuppressWarnings("unchecked")
		List<Object[]> infoDetails = entityManager.createNativeQuery("select * from infodetails").getResultList();
		assertEquals("the infoDetails table exists", 1, infoDetails.size());
		assertFalse("the infoDetails is now visible", (boolean) infoDetails.get(0)[2]);
	}

	@Test
	public void testMergeOneToOne() {
		logger.info("starting merge one to one cascade test");
		testPersistOneToOne();
		Info info = (Info) entityManager.createQuery("from Info").getSingleResult();
		info.getDetails().setVisible(true);
		try {
			userTransaction.begin();
			entityManager.merge(info);
			userTransaction.commit();
		} catch (NotSupportedException | SystemException | IllegalStateException | SecurityException
				| HeuristicMixedException | HeuristicRollbackException | RollbackException e) {
			fail();
		}
		@SuppressWarnings("unchecked")
		List<Info> infos = entityManager.createNativeQuery("select * from info").getResultList();
		assertEquals("the info table exists", 1, infos.size());
		@SuppressWarnings("unchecked")
		List<Object[]> infoDetails = entityManager.createNativeQuery("select * from infodetails").getResultList();
		assertEquals("the infoDetails table exists", 1, infoDetails.size());
		assertTrue("the infoDetails is now visible", (boolean) infoDetails.get(0)[2]);
	}

	@Test
	public void testDeleteOneToOne() {
		logger.info("starting merge one to one cascade test");
		testPersistOneToOne();
		try {
			userTransaction.begin();
			Info info = (Info) entityManager.createQuery("from Info").getSingleResult();
			entityManager.remove(info);
			userTransaction.commit();
		} catch (NotSupportedException | SystemException | IllegalStateException | SecurityException
				| HeuristicMixedException | HeuristicRollbackException | RollbackException e) {
			fail();
		}
		@SuppressWarnings("unchecked")
		List<Info> infos = entityManager.createNativeQuery("select * from info").getResultList();
		assertEquals("the info table has not values", 0, infos.size());
		@SuppressWarnings("unchecked")
		List<Object[]> infoDetails = entityManager.createNativeQuery("select * from infodetails").getResultList();
		assertEquals("the infoDetails table has not values", 0, infoDetails.size());
	}

	@Test
	public void testDeleteChildOneToOne() {
		logger.info("starting merge one to one cascade test");
		testPersistOneToOne();
		try {
			userTransaction.begin();
			Info info = (Info) entityManager.createQuery("from Info").getSingleResult();
			info.removeDetails();
			userTransaction.commit();
		} catch (NotSupportedException | SystemException | IllegalStateException | SecurityException
				| HeuristicMixedException | HeuristicRollbackException | RollbackException e) {
			fail();
		}
		@SuppressWarnings("unchecked")
		List<Info> infos = entityManager.createNativeQuery("select * from info").getResultList();
		assertEquals("the info table has values", 1, infos.size());
		@SuppressWarnings("unchecked")
		List<Object[]> infoDetails = entityManager.createNativeQuery("select * from infodetails").getResultList();
		assertEquals("the infoDetails table has not values", 0, infoDetails.size());
	}

	@Test
	public void testPersistOneToMany() {
		logger.info("starting persist one to one cascade test");
		Info info = new Info();
		info.setName("Wildfly Book Info");
		Comment comment1 = new Comment();
		comment1.setReview("Good book!");
		Comment comment2 = new Comment();
		comment2.setReview("Nice book!");
		info.addComment(comment1);
		info.addComment(comment2);
		try {
			userTransaction.begin();
			clean();
			entityManager.persist(info);
			userTransaction.commit();
		} catch (NotSupportedException | SystemException | IllegalStateException | SecurityException
				| HeuristicMixedException | HeuristicRollbackException | RollbackException e) {
			fail();
		}
		@SuppressWarnings("unchecked")
		List<Info> infos = entityManager.createNativeQuery("select * from info").getResultList();
		assertEquals("the info table exists", 1, infos.size());
		@SuppressWarnings("unchecked")
		List<Object[]> comments = entityManager.createNativeQuery("select * from comment").getResultList();
		assertEquals("the infoDetails table exists", 2, comments.size());
		assertEquals("the infoDetails is now visible", "Good book!", comments.get(0)[1]);
	}
}
