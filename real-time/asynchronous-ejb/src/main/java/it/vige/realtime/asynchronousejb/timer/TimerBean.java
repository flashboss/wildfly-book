package it.vige.realtime.asynchronousejb.timer;

import static java.util.logging.Level.SEVERE;
import static java.util.logging.Logger.getLogger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerHandle;
import javax.ejb.TimerService;

@Singleton
public class TimerBean {

	@Resource
	private TimerService timerService;

	private static final Logger logger = getLogger(TimerBean.class.getName());

	@Timeout
	public void timeout(Timer timer) {
		logger.info("TimerEJB: timeout occurred");
		timer.getTimeRemaining();
	}

	public void programmaticTimeout() {
		logger.info("TimerEJB: programmatic timeout occurred");
		long duration = 6000;
		Timer timer = timerService.createSingleActionTimer(duration, new TimerConfig());
		timer.getInfo();
		try {
			timer.getSchedule();
		} catch (IllegalStateException e) {
			logger.log(SEVERE, "it is not a scheduler", e);
		}
		TimerHandle timerHandle = timer.getHandle();
		timerHandle.getTimer();
		timer.isCalendarTimer();
		timer.isPersistent();
		timer.getTimeRemaining();
	}

	public void timerConfig() {
		logger.info("TimerEJB: timer configuration");
		DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy 'at' HH:mm");
		Date date = null;
		try {
			date = formatter.parse("05/01/2010 at 12:05");
			TimerConfig timerConfig = new TimerConfig();
			timerConfig.getInfo();
			timerConfig.isPersistent();
			Timer timer = timerService.createSingleActionTimer(date, timerConfig);
			timer.getTimeRemaining();
		} catch (ParseException e) {
			logger.log(SEVERE, "formatting error", e);
		}
	}
}
