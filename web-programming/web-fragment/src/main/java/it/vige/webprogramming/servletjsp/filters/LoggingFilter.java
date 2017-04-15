package it.vige.webprogramming.servletjsp.filters;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

@WebFilter(filterName = "LoggingFilter", urlPatterns = { "/*" })
public class LoggingFilter implements Filter {

	private static final boolean debug = true;

	private FilterConfig filterConfig = null;

	public LoggingFilter() {
	}

	private void doBeforeProcessing(ServletRequest request, ServletResponse response)
			throws IOException, ServletException {
		if (debug) {
			log("LoggingFilter:DoBeforeProcessing");
		}

		for (Enumeration<String> en = request.getParameterNames(); en.hasMoreElements();) {
			String name = (String) en.nextElement();
			String values[] = request.getParameterValues(name);
			int n = values.length;
			StringBuilder buf = new StringBuilder();
			buf.append(name);
			buf.append("=");
			for (int i = 0; i < n; i++) {
				buf.append(values[i]);
				if (i < n - 1) {
					buf.append(",");
				}
			}
			log(buf.toString());
		}
	}

	private void doAfterProcessing(ServletRequest request, ServletResponse response)
			throws IOException, ServletException {
		if (debug) {
			log("LoggingFilter:DoAfterProcessing");
		}

		for (Enumeration<String> en = request.getAttributeNames(); en.hasMoreElements();) {
			String name = (String) en.nextElement();
			Object value = request.getAttribute(name);
			log("attribute: " + name + "=" + value.toString());

		}
		PrintWriter respOut = new PrintWriter(response.getWriter());
		respOut.println("<P><B>This has been appended by an intrusive filter.</B>");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		if (debug) {
			log("LoggingFilter:doFilter()");
		}

		doBeforeProcessing(request, response);

		Throwable problem = null;
		try {
			chain.doFilter(request, response);
		} catch (IOException | ServletException t) {
			problem = t;
			t.printStackTrace();
		}

		doAfterProcessing(request, response);

		if (problem != null) {
			if (problem instanceof ServletException) {
				throw (ServletException) problem;
			}
			if (problem instanceof IOException) {
				throw (IOException) problem;
			}
			sendProcessingError(problem, response);
		}
	}

	public FilterConfig getFilterConfig() {
		return (this.filterConfig);
	}

	public void setFilterConfig(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}

	@Override
	public void destroy() {
	}

	@Override
	public void init(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
		if (filterConfig != null) {
			if (debug) {
				log("LoggingFilter:Initializing filter");
			}
		}
	}

	@Override
	public String toString() {
		if (filterConfig == null) {
			return ("LoggingFilter()");
		}
		StringBuilder sb = new StringBuilder("LoggingFilter(");
		sb.append(filterConfig);
		sb.append(")");
		return (sb.toString());
	}

	private void sendProcessingError(Throwable t, ServletResponse response) {
		String stackTrace = getStackTrace(t);

		if (stackTrace != null && !stackTrace.equals("")) {
			try {
				response.setContentType("text/html");
				try (PrintStream ps = new PrintStream(response.getOutputStream());
						PrintWriter pw = new PrintWriter(ps)) {
					pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); // NOI18N

					pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
					pw.print(stackTrace);
					pw.print("</pre></body>\n</html>"); // NOI18N
				}
				response.getOutputStream().close();
			} catch (IOException ex) {
			}
		} else {
			try {
				try (PrintStream ps = new PrintStream(response.getOutputStream())) {
					t.printStackTrace(ps);
				}
				response.getOutputStream().close();
			} catch (IOException ex) {
			}
		}
	}

	public static String getStackTrace(Throwable t) {
		String stackTrace = null;
		try {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			t.printStackTrace(pw);
			pw.close();
			sw.close();
			stackTrace = sw.getBuffer().toString();
		} catch (IOException ex) {
		}
		return stackTrace;
	}

	public void log(String msg) {
		filterConfig.getServletContext().log(msg);
	}

}