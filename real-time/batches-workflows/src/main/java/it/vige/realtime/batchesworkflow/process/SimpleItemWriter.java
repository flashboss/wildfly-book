package it.vige.realtime.batchesworkflow.process;

import static java.util.logging.Logger.getLogger;

import java.util.List;
import java.util.logging.Logger;

import javax.batch.api.chunk.AbstractItemWriter;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Named
public class SimpleItemWriter extends AbstractItemWriter {

	private static final Logger logger = getLogger(SimpleItemWriter.class.getName());

	@PersistenceContext
	private EntityManager em;

	public void writeItems(List<Object> list) throws Exception {
		for (Object obj : list) {
			logger.info("PayrollRecord: " + obj);
			em.persist(obj);
		}
	}

}
