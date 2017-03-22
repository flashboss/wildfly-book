package it.vige.realtime.batchesworkflow.mail;

import javax.annotation.Resource;
import javax.batch.api.AbstractBatchlet;
import javax.batch.runtime.context.JobContext;
import javax.batch.runtime.context.StepContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Named

public class MailBatchlet extends AbstractBatchlet {
	@Resource(mappedName = "java:jboss/mail/Default")
	private Session mailSession;

	@Inject
	StepContext stepContext;
	@Inject
	JobContext jobContext;

	@Override
	public String process() {
		System.out.println("Running inside MailBatchlet batchlet ");
		String fromAddress = stepContext.getProperties().getProperty("mail.from");
		String toAddress = stepContext.getProperties().getProperty("mail.to");

		try {
			MimeMessage m = new MimeMessage(mailSession);
			Address from = new InternetAddress(fromAddress);
			Address[] to = new InternetAddress[] { new InternetAddress(toAddress) };

			m.setFrom(from);
			m.setRecipients(Message.RecipientType.TO, to);
			m.setSubject("WildFly Mail");
			m.setSentDate(new java.util.Date());
			m.setContent("Job Execution id " + jobContext.getExecutionId() + " warned disk space getting low!",
					"text/plain");
			Transport.send(m);

		} catch (javax.mail.MessagingException e) {
			e.printStackTrace();

		}
		return "COMPLETED";
	}

}
