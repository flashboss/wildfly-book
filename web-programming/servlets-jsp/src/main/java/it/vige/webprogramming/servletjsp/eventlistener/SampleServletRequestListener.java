package it.vige.webprogramming.servletjsp.eventlistener;

import static java.util.logging.Logger.getLogger;

import java.util.logging.Logger;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class SampleServletRequestListener implements ServletRequestListener {

	private static final Logger logger = getLogger(SampleServletRequestListener.class.getName());

	@Override
	public void requestDestroyed(ServletRequestEvent sre) {
		logger.info("MyServletRequestListener.requestDestroyed: " + sre.getServletContext().getContextPath());
	}

	@Override
	public void requestInitialized(ServletRequestEvent sre) {
		logger.info("MyServletRequestListener.requestInitialized: " + sre.getServletContext().getContextPath());
	}
}
