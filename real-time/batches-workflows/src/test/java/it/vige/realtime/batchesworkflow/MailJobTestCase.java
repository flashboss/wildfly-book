package it.vige.realtime.batchesworkflow;

import static java.util.logging.Level.SEVERE;
import static java.util.logging.Logger.getLogger;
import static javax.batch.runtime.BatchRuntime.getJobOperator;
import static org.jboss.shrinkwrap.api.ShrinkWrap.create;
import static org.jboss.shrinkwrap.api.asset.EmptyAsset.INSTANCE;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.Properties;
import java.util.logging.Logger;

import javax.batch.operations.JobOperator;
import javax.batch.operations.JobSecurityException;
import javax.batch.operations.JobStartException;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.asset.FileAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import it.vige.realtime.batchesworkflow.mail.MailBatchlet;

@RunWith(Arquillian.class)
public class MailJobTestCase {

	private static final Logger logger = getLogger(MailJobTestCase.class.getName());

	private final static String JOB_NAME = "Sendmail";

	@Deployment
	public static WebArchive createWebDeployment() {
		final WebArchive war = create(WebArchive.class, "simplejob-test.war");
		war.addPackage(MailBatchlet.class.getPackage());
		war.addAsManifestResource(INSTANCE, "beans.xml");
		war.addAsWebInfResource(new FileAsset(new File("src/main/resources/META-INF/batch-jobs/" + JOB_NAME + ".xml")),
				"classes/META-INF/batch-jobs/" + JOB_NAME + ".xml");
		return war;
	}

	@Test
	public void startProcess() throws Exception {
		logger.info("starting mail process test");
		long executionId = 0;
		try {
			JobOperator jo = getJobOperator();

			Properties p = new Properties();
			p.setProperty("source", "/home/user1/file.dmp");
			p.setProperty("destination", "/usr/share/dmp");
			p.setProperty("filesystem", "/usr/");
			executionId = jo.start("bpmJob", p);

			logger.info("Job submitted: " + executionId);

		} catch (JobStartException | JobSecurityException ex) {
			logger.log(SEVERE, "Error submitting Job! " + ex);
		}
		assertNotNull("executionId not empty", executionId);
	}

}
