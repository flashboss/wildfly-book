package it.vige.businesscomponents.businesslogic;

import static java.util.logging.Logger.getLogger;
import static javax.naming.Context.URL_PKG_PREFIXES;
import static org.jboss.shrinkwrap.api.ShrinkWrap.create;

import java.io.File;
import java.util.Properties;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.asset.FileAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import it.vige.businesscomponents.businesslogic.remote.Machine;

@RunWith(Arquillian.class)
public class RemotingEJBClientTestCase {

	private static final Logger logger = getLogger(RemotingEJBClientTestCase.class.getName());

	private Context context = null;

	@Deployment
	public static JavaArchive createEJBDeployment() {
		final JavaArchive jar = create(JavaArchive.class, "remoting-ejb-client-test.jar");
		jar.addPackage(Machine.class.getPackage());
		jar.addAsManifestResource(new FileAsset(new File("src/test/resources/META-INF/ejb-jar-remoting.xml")),
				"ejb-jar.xml");
		return jar;
	}

	private void execute() throws NamingException {
		Machine machine = lookup();
		logger.info(machine + "");
		int result = machine.go(1);
		logger.info(result + "");
	}

	@Test
	public void testEJBClient() throws Exception {
		logger.info("starting remoting ejb client test");

		try {
			createInitialContext();
			execute();
		} finally {
			closeContext();
		}
	}

	private Machine lookup() throws NamingException {

		final String appName = "test";
		final String moduleName = "remoting";
		final String distinctName = "";
		final String beanName = "machine";
		final String viewClassName = Machine.class.getName();

		Machine calc = (Machine) context.lookup(
				"ejb:" + appName + "/" + moduleName + "/" + distinctName + "/" + beanName + "!" + viewClassName);

		return calc;
	}

	private void createInitialContext() throws NamingException {
		Properties prop = new Properties();
		prop.put(URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");

		context = new InitialContext(prop);
	}

	private void closeContext() throws NamingException {
		if (context != null) {
			context.close();
		}
	}
}
