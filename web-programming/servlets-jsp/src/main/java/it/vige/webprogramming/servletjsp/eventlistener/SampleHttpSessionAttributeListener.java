package it.vige.webprogramming.servletjsp.eventlistener;

import static java.util.logging.Logger.getLogger;

import java.util.logging.Logger;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

@WebListener
public class SampleHttpSessionAttributeListener implements HttpSessionAttributeListener {

	private static final Logger logger = getLogger(SampleHttpSessionAttributeListener.class.getName());

	@Override
	public void attributeAdded(HttpSessionBindingEvent event) {
		logger.info("MyHttpSessionAttributeListener.attributeAdded: " + event.getName());
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent event) {
		logger.info("MyHttpSessionAttributeListener.attributeRemoved: " + event.getName());
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent event) {
		logger.info("MyHttpSessionAttributeListener.attributeReplaced: " + event.getName());
	}
}
