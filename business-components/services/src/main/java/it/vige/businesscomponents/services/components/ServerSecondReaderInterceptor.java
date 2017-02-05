package it.vige.businesscomponents.services.components;

import static java.util.logging.Logger.getLogger;
import static javax.ws.rs.Priorities.ENTITY_CODER;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import javax.annotation.Priority;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.ReaderInterceptor;
import javax.ws.rs.ext.ReaderInterceptorContext;

@Provider
@Priority(ENTITY_CODER + 20)
public class ServerSecondReaderInterceptor implements ReaderInterceptor {

	private static final Logger logger = getLogger(ServerSecondReaderInterceptor.class.getName());

	@Override
	public Object aroundReadFrom(ReaderInterceptorContext interceptorContext)
			throws IOException, WebApplicationException {
		logger.info("ServerSecondReaderInterceptor invoked.");
		InputStream inputStream = interceptorContext.getInputStream();
		byte[] bytes = new byte[inputStream.available()];
		inputStream.read(bytes);
		String requestContent = new String(bytes);
		requestContent = requestContent + " Request changed in ServerSecondReaderInterceptor.";
		interceptorContext.setInputStream(new ByteArrayInputStream(requestContent.getBytes()));
		return interceptorContext.proceed();
	}
}
