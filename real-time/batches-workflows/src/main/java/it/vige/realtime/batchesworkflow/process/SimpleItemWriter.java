package it.vige.realtime.batchesworkflow.process;

import static java.util.logging.Logger.getLogger;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.logging.Logger;

import javax.batch.api.chunk.AbstractItemWriter;
import javax.inject.Named;

import org.jboss.logmanager.Level;

@Named
public class SimpleItemWriter extends AbstractItemWriter {

	private static final Logger logger = getLogger(SimpleItemWriter.class.getName());

	public void writeItems(List<Object> list) throws Exception {
		for (Object obj : list) {
			logger.info("PayrollRecord: " + obj);
			serialize(obj);
		}
	}

	private void serialize(Object obj) {
		try (FileOutputStream fo = new FileOutputStream("evolve.tmp")) {
			ObjectOutputStream so = new ObjectOutputStream(fo);
			so.writeObject(obj);
			so.flush();
		} catch (Exception ex) {
			logger.log(Level.SEVERE, ex.getMessage(), ex);
		}

	}

}
