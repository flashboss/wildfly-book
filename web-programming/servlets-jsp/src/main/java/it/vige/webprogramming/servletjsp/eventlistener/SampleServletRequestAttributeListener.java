package it.vige.webprogramming.servletjsp.eventlistener;

import static java.util.logging.Logger.getLogger;

import java.util.logging.Logger;

import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class SampleServletRequestAttributeListener implements ServletRequestAttributeListener {

	private static final Logger logger = getLogger(SampleServletRequestAttributeListener.class.getName());

	@Override
	public void attributeAdded(ServletRequestAttributeEvent srae) {
		logger.info("MyServletRequestAttributeListener.attributeAdded: " + srae.getName());
	}

	@Override
	public void attributeRemoved(ServletRequestAttributeEvent srae) {
		logger.info("MyServletRequestAttributeListener.attributeRemoved: " + srae.getName());
	}

	@Override
	public void attributeReplaced(ServletRequestAttributeEvent srae) {
		logger.info("MyServletRequestAttributeListener.attributeReplaced: " + srae.getName());
	}

}
