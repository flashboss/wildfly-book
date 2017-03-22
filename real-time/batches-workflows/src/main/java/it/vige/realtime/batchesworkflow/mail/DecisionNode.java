package it.vige.realtime.batchesworkflow.mail;

import java.io.File;
import java.util.Properties;

import javax.batch.api.Decider;
import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.StepExecution;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class DecisionNode implements Decider {

	@Inject
	JobContext jobContext;

	@Override
	public String decide(StepExecution[] ses) throws Exception {
		Properties parameters = getParameters();
		String fs = parameters.getProperty("filesystem");
		File file = new File(fs);
		long totalSpace = file.getTotalSpace();
		if (totalSpace > 100000) {
			return "DSK_SPACE_OK";
		} else {
			return "DSK_SPACE_LOW";
		}
	}

	private Properties getParameters() {
		JobOperator operator = BatchRuntime.getJobOperator();
		return operator.getParameters(jobContext.getExecutionId());

	}
}
