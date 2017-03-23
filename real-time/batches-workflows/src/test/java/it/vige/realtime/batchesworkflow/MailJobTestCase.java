package it.vige.realtime.batchesworkflow;

import static java.util.logging.Level.SEVERE;
import static java.util.logging.Logger.getLogger;
import static javax.batch.runtime.BatchRuntime.getJobOperator;
import static org.jboss.shrinkwrap.api.ShrinkWrap.create;
import static org.jboss.shrinkwrap.api.asset.EmptyAsset.INSTANCE;
import static org.jboss.shrinkwrap.resolver.api.maven.Maven.resolver;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.logging.Logger;

import javax.batch.operations.JobOperator;
import javax.batch.operations.JobSecurityException;
import javax.batch.operations.JobStartException;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.asset.FileAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.subethamail.smtp.MessageContext;
import org.subethamail.smtp.MessageHandler;
import org.subethamail.smtp.MessageHandlerFactory;
import org.subethamail.smtp.RejectException;
import org.subethamail.smtp.server.SMTPServer;

import it.vige.realtime.batchesworkflow.mail.MailBatchlet;

@RunWith(Arquillian.class)
public class MailJobTestCase {

	private static final Logger logger = getLogger(MailJobTestCase.class.getName());

	private MyMessageHandlerFactory myFactory = new MyMessageHandlerFactory();
	private SMTPServer smtpServer = new SMTPServer(myFactory);

	private final static String JOB_NAME = "Sendmail";

	@Deployment
	public static WebArchive createWebDeployment() {
		final WebArchive war = create(WebArchive.class, "mailjob-test.war");
		File[] files = resolver().loadPomFromFile("pom.xml").importRuntimeDependencies()
				.resolve("org.subethamail:subethasmtp:3.1.7").withTransitivity().asFile();
		war.addPackage(MailBatchlet.class.getPackage());
		war.addAsManifestResource(INSTANCE, "beans.xml");
		war.addAsWebInfResource(new FileAsset(new File("src/main/resources/META-INF/batch-jobs/" + JOB_NAME + ".xml")),
				"classes/META-INF/batch-jobs/" + JOB_NAME + ".xml");
		war.addAsLibraries(files);
		return war;
	}

	@Before
	public void init() {
		smtpServer.setPort(25000);
		smtpServer.start();
	}

	@After
	public void end() {
		smtpServer.stop();
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
			executionId = jo.start(JOB_NAME, p);

			logger.info("Job submitted: " + executionId);

		} catch (JobStartException | JobSecurityException ex) {
			logger.log(SEVERE, "Error submitting Job! " + ex);
		}
		assertNotNull("executionId not empty", executionId);
		assertTrue("message received",
				myFactory.getBody().equals("Job Execution id " + executionId + " warned disk space getting low!"));
	}

	private class MyMessageHandlerFactory implements MessageHandlerFactory {

		private String body;

		public MessageHandler create(MessageContext ctx) {
			return new Handler(ctx);
		}

		class Handler implements MessageHandler {

			public Handler(MessageContext ctx) {
			}

			public void from(String from) throws RejectException {
				logger.info("FROM:" + from);
			}

			public void recipient(String recipient) throws RejectException {
				logger.info("RECIPIENT:" + recipient);
			}

			public void data(InputStream data) throws IOException {
				logger.info("MAIL DATA");
				logger.info("= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =");
				body = this.convertStreamToString(data);
				logger.info(body);
				logger.info("= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =");
			}

			public void done() {
				logger.info("Finished");
			}

			public String convertStreamToString(InputStream is) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(is));
				StringBuilder sb = new StringBuilder();

				String line = null;
				try {
					while ((line = reader.readLine()) != null) {
						sb.append(line + "\n");
					}
				} catch (IOException e) {
					logger.log(SEVERE, "activiti diagram", e);
				}
				return sb.toString();
			}

		}

		public String getBody() {
			return body;
		}
	}
}
