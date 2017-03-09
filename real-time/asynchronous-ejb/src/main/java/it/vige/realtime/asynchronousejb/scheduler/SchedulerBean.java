package it.vige.realtime.asynchronousejb.scheduler;

import static java.util.logging.Logger.getLogger;

import java.util.Date;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Schedules;
import javax.ejb.Singleton;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;

@Singleton
public class SchedulerBean {

	private static final Logger logger = getLogger(SchedulerBean.class.getName());

	@Resource
	private TimerService timerService;

	private Date lastProgrammaticTimeout;
	private Date lastAutomaticTimeout;

	public void setTimer(long intervalDuration) {
		logger.info("Setting a programmatic timeout for " + intervalDuration + " milliseconds from now.");
		Timer timer = timerService.createTimer(intervalDuration, "Created new programmatic timer");
		logger.info("created timer: " + timer);
	}

	@Timeout
	public void programmaticTimeout(Timer timer) {
		this.setLastProgrammaticTimeout(new Date());
		logger.info("Programmatic timeout occurred.");
	}

	@Schedule(minute = "*/3", hour = "*")
	public void automaticTimeout() {
		this.setLastAutomaticTimeout(new Date());
		logger.info("Automatic timeout occured");
	}

	@Schedule(dayOfWeek = "Sun", hour = "0")
	public void cleanupWeekData() {
		logger.info("started the scheduler");
	}

	@Schedules({ @Schedule(dayOfMonth = "Last"), @Schedule(dayOfWeek = "Fri", hour = "23") })
	public void doPeriodicCleanup() {
		logger.info("multi scheduler example");
	}

	public String getLastProgrammaticTimeout() {
		if (lastProgrammaticTimeout != null) {
			return lastProgrammaticTimeout.toString();
		} else {
			return "never";
		}

	}

	public void setLastProgrammaticTimeout(Date lastTimeout) {
		this.lastProgrammaticTimeout = lastTimeout;
	}

	public String getLastAutomaticTimeout() {
		if (lastAutomaticTimeout != null) {
			return lastAutomaticTimeout.toString();
		} else {
			return "never";
		}
	}

	public void setLastAutomaticTimeout(Date lastAutomaticTimeout) {
		this.lastAutomaticTimeout = lastAutomaticTimeout;
	}
}
