package it.vige.businesscomponents.injection;

import static it.vige.businesscomponents.injection.util.ConfigurationKey.DEFAULT_DIRECTORY;
import static java.util.logging.Logger.getLogger;
import static org.jboss.shrinkwrap.api.ShrinkWrap.create;
import static org.jboss.shrinkwrap.api.asset.EmptyAsset.INSTANCE;
import static org.junit.Assert.assertEquals;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.annotation.ManagedBean;
import javax.annotation.Resource;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.util.AnnotationLiteral;
import javax.enterprise.util.TypeLiteral;
import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import it.vige.businesscomponents.injection.util.ConfigurationKey;
import it.vige.businesscomponents.injection.util.ConfigurationValue;

@RunWith(Arquillian.class)
public class UtilTestCase {

	private static final Logger logger = getLogger(UtilTestCase.class.getName());

	@Resource(lookup = "java:comp/BeanManager")
	private BeanManager beanManager;
	
	@Deployment
	public static JavaArchive createJavaDeployment() {
		final JavaArchive jar = create(JavaArchive.class, "event-test.jar");
		jar.addPackage(ConfigurationKey.class.getPackage());
		jar.addAsManifestResource(INSTANCE, "beans.xml");
		return jar;
	}

	@Inject
	@ConfigurationValue(key = DEFAULT_DIRECTORY)
	private String defaultDirectory;

	/**
	 * Tests annotation literals in a jar archive
	 */
	@Test
	public void testAnnotationLiteral() {
		logger.info("staring util event test");
		Set<Bean<?>> beans = beanManager.getBeans(ManagedBean.class, new AnnotationLiteral<Any>() {

			private static final long serialVersionUID = -4378964126487759035L;
		});
		if (beans.size() > 0) {
			Bean<?> bean = beans.iterator().next();
			CreationalContext<?> cc = beanManager.createCreationalContext(bean);
			beanManager.getReference(bean, ManagedBean.class, cc);
		} else {
			logger.info("Can't find class " + ManagedBean.class);
		}
	}

	/**
	 * Tests type literals in a jar archive
	 */
	@Test
	public void testTypeLiteral() {
		new HashSet<Type>(Arrays.asList(Integer.class, String.class, new TypeLiteral<List<Boolean>>() {

			private static final long serialVersionUID = 1228070460716823527L;
		}.getType()));
	}

	/**
	 * Tests non binding in a jar archive
	 */
	@Test
	public void testNonBinding() {
		assertEquals("Verify if the NonBinding works", "prova", defaultDirectory);
	}
}
