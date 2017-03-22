package it.vige.realtime.batchesworkflow;

import static java.util.logging.Logger.getLogger;
import static javax.batch.runtime.BatchRuntime.getJobOperator;
import static org.jboss.shrinkwrap.api.ShrinkWrap.create;
import static org.jboss.shrinkwrap.api.asset.EmptyAsset.INSTANCE;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.Properties;
import java.util.logging.Logger;

import javax.batch.operations.JobOperator;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.asset.FileAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import it.vige.realtime.batchesworkflow.bean.Payroll;
import it.vige.realtime.batchesworkflow.process.SimpleItemProcessor;

@RunWith(Arquillian.class)
public class SimpleJobTestCase {

	private static final Logger logger = getLogger(SimpleJobTestCase.class.getName());

	private final static String JOB_NAME = "SimplePayrollJob";

	@Deployment
	public static WebArchive createWebDeployment() {
		final WebArchive war = create(WebArchive.class, "simplejob-test.war");
		war.addPackage(Payroll.class.getPackage());
		war.addPackage(SimpleItemProcessor.class.getPackage());
		war.addAsManifestResource(new FileAsset(new File("src/main/resources/META-INF/persistence.xml")),
				"persistence.xml");
		war.addAsManifestResource(INSTANCE, "beans.xml");
		war.addAsWebInfResource(new FileAsset(new File("src/main/resources/META-INF/batch-jobs/" + JOB_NAME + ".xml")),
				"classes/META-INF/batch-jobs/" + JOB_NAME + ".xml");
		return war;
	}

	@Test
	public void startProcess() throws Exception {
		logger.info("starting process test");
		JobOperator jobOperator = getJobOperator();
		Properties props = new Properties();
		props.setProperty("payrollInputDataFileName", "MY_NEW_PROPERTY");
		long executionId = jobOperator.start(JOB_NAME, props);
		assertNotNull("executionId not empty", executionId);
	}

}
