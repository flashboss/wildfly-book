package it.vige.realtime.batchesworkflow.process;

import static java.util.logging.Logger.getLogger;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Properties;
import java.util.logging.Logger;

import javax.batch.api.chunk.AbstractItemReader;
import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;
import javax.inject.Named;

import it.vige.realtime.batchesworkflow.bean.PayrollInputRecord;

@Named
public class SimpleItemReader extends AbstractItemReader {
	
	private static final Logger logger = getLogger(SimpleItemReader.class.getName());

	@Inject
	private JobContext jobContext;

	private int recordNumber;
	private InputStream inputStream;
	private BufferedReader br;
	private String line;

	@Override
	public void open(Serializable prevCheckpointInfo) throws Exception {
		JobOperator jobOperator = BatchRuntime.getJobOperator();
		Properties jobParameters = jobOperator.getParameters(jobContext.getExecutionId());
		String resourceName = (String) jobParameters.get("payrollInputDataFileName");
		inputStream = new FileInputStream(resourceName);
		br = new BufferedReader(new InputStreamReader(inputStream));

		if (prevCheckpointInfo != null)
			recordNumber = (Integer) prevCheckpointInfo;
		for (int i = 1; i < recordNumber; i++) { // Skip upto recordNumber
			br.readLine();
		}
		logger.info("[SimpleItemReader] Opened Payroll file for reading from record number: " + recordNumber);
	}

	@Override
	public Object readItem() throws Exception {
		Object record = null;
		if (line != null) {
			String[] fields = line.split("[, \t\r\n]+");
			PayrollInputRecord payrollInputRecord = new PayrollInputRecord();
			payrollInputRecord.setId(Integer.parseInt(fields[0]));
			payrollInputRecord.setBaseSalary(Integer.parseInt(fields[1]));
			record = payrollInputRecord;
			// Now that we could successfully read, Increment the record number
			recordNumber++;
		}
		return record;
	}

	@Override
	public Serializable checkpointInfo() throws Exception {
		return recordNumber;
	}
}
