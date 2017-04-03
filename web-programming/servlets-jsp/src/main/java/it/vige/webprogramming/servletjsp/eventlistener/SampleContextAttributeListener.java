package it.vige.webprogramming.servletjsp.eventlistener;

import static java.util.logging.Logger.getLogger;

import java.util.logging.Logger;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class SampleContextAttributeListener implements ServletContextAttributeListener {

	private static final Logger logger = getLogger(SampleContextAttributeListener.class.getName());

	@Override
	public void attributeAdded(ServletContextAttributeEvent event) {
		logger.info("MyContextAttributeListener.attributeAdded: " + event.getName());
	}

	@Override
	public void attributeRemoved(ServletContextAttributeEvent event) {
		logger.info("MyContextAttributeListener.attributeRemoved: " + event.getName());
	}

	@Override
	public void attributeReplaced(ServletContextAttributeEvent event) {
		logger.info("MyContextAttributeListener.attributeReplaced: " + event.getName());
	}
}
