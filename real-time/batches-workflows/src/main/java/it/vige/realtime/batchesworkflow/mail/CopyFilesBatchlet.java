package it.vige.realtime.batchesworkflow.mail;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Properties;

import javax.batch.api.AbstractBatchlet;
import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class CopyFilesBatchlet extends AbstractBatchlet {
	@Inject
	JobContext jobContext;

	@Override
	public String process() {

		System.out.println("Running inside SendBillBatchlet batchlet ");

		Properties parameters = getParameters();
		String source = parameters.getProperty("source");
		String destination = parameters.getProperty("destination");

		// JDK 1.7 API
		try {
			Files.copy(new File(source).toPath(), new File(destination).toPath());
			return "COMPLETED";
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "FAILED";

	}

	private Properties getParameters() {
		JobOperator operator = BatchRuntime.getJobOperator();
		return operator.getParameters(jobContext.getExecutionId());

	}

}
