package it.vige.businesscomponents.persistence;

import static java.util.logging.Logger.getLogger;
import static org.jboss.shrinkwrap.api.ShrinkWrap.create;
import static org.jboss.shrinkwrap.api.asset.EmptyAsset.INSTANCE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.asset.FileAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class CommonTestCase {

	private static final Logger logger = getLogger(CommonTestCase.class.getName());

	@PersistenceContext
	private EntityManager entityManager;

	@Deployment
	public static JavaArchive createJavaDeployment() {
		final JavaArchive jar = create(JavaArchive.class, "common-test.jar");
		jar.addPackage(Forum.class.getPackage());
		jar.addAsManifestResource(INSTANCE, "beans.xml");
		jar.addAsManifestResource(new FileAsset(new File("src/test/resources/META-INF/persistence-test.xml")),
				"persistence.xml");
		jar.addAsResource(new FileAsset(new File("src/main/resources/forums.import.sql")), "forums.import.sql");
		return jar;
	}

	/**
	 * Tests annotation literals in a jar archive
	 */
	@Test
	@SuppressWarnings("unchecked")
	public void testSqlScript() {
		logger.info("starting util event test");
		assertNotNull("The injected entity manager", entityManager);
		List<Category> categories = entityManager.createNamedQuery("findCategories").getResultList();
		assertEquals("one category created by the sql script", 1, categories.size());
		List<Forum> forums = entityManager.createNamedQuery("findForumsByCategoryId")
				.setParameter("categoryId", categories.get(0)).getResultList();
		assertEquals("two forums created by the sql script", 2, forums.size());
	}
}
