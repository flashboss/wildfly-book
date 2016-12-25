package it.vige.businesscomponents.injection.event;

import static java.util.logging.Logger.getLogger;
import static javax.enterprise.event.Reception.IF_EXISTS;
import static javax.enterprise.event.TransactionPhase.AFTER_COMPLETION;
import static javax.enterprise.event.TransactionPhase.AFTER_FAILURE;
import static javax.enterprise.event.TransactionPhase.AFTER_SUCCESS;
import static javax.enterprise.event.TransactionPhase.BEFORE_COMPLETION;

import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;

@ApplicationScoped
public class Observer {

	private static final Logger logger = getLogger(Observer.class.getName());

	public void ifExistsAfterFailure(@Observes(notifyObserver = IF_EXISTS, during = AFTER_FAILURE) Event<Bill> event) {
		logger.info("observing if exists after failure");
	}

	public void ifExistsAfterSuccess(@Observes(notifyObserver = IF_EXISTS, during = AFTER_SUCCESS) Event<Bill> event) {
		logger.info("observing if exists after success");
	}

	public void ifExistsBeforeCompletion(
			@Observes(notifyObserver = IF_EXISTS, during = BEFORE_COMPLETION) Event<Bill> event) {
		logger.info("observing if exists before completion");
	}

	public void ifExistsAfterCompletion(
			@Observes(notifyObserver = IF_EXISTS, during = AFTER_COMPLETION) Event<Bill> event) {
		logger.info("observing if exists after completion");
	}

	public void ifExistsInProgress(@Observes(notifyObserver = IF_EXISTS) Event<Bill> event) {
		logger.info("observing if exists after completion");
	}

	public void alwaysAfterFailure(@Observes(during = AFTER_FAILURE) Event<Bill> event) {
		logger.info("observing if exists after failure");
	}

	public void alwaysAfterSuccess(@Observes(during = AFTER_SUCCESS) Event<Bill> event) {
		logger.info("observing if exists after success");
	}

	public void alwaysBeforeCompletion(@Observes(during = BEFORE_COMPLETION) Event<Bill> event) {
		logger.info("observing if exists before completion");
	}

	public void alwaysAfterCompletion(@Observes(during = AFTER_COMPLETION) Event<Bill> event) {
		logger.info("observing if exists after completion");
	}

	public void alwaysInProgress(@Observes Event<Bill> event) {
		logger.info("observing if exists after completion");
	}
}
