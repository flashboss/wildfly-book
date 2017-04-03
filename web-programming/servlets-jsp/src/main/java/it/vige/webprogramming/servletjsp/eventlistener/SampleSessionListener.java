package it.vige.webprogramming.servletjsp.eventlistener;

import static java.util.logging.Logger.getLogger;

import java.util.logging.Logger;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class SampleSessionListener implements HttpSessionListener {

	private static final Logger logger = getLogger(SampleSessionListener.class.getName());

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		logger.info("MySessionListener.sessionCreated: " + se.getSession().getId());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		logger.info("MySessionListener.sessionDestroyed: " + se.getSession().getId());
	}
}
