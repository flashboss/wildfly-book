package it.vige.webprogramming.servletjsp.eventlistener;

import static java.util.logging.Logger.getLogger;

import java.util.logging.Logger;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

public class SampleHttpSessionBindingListener implements HttpSessionBindingListener {

	private static final Logger logger = getLogger(SampleHttpSessionBindingListener.class.getName());

	@Override
	public void valueBound(HttpSessionBindingEvent event) {
		logger.info("MyHttpSessionBindingListener.valueBound: " + event.getName());
	}

	@Override
	public void valueUnbound(HttpSessionBindingEvent event) {
		logger.info("MyHttpSessionBindingListener.valueUnbound: " + event.getName());
	}

}
