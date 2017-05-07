package it.vige.clustering.websessions;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/SesssionServlet")
public class SessionServlet extends HttpServlet {

	private static final long serialVersionUID = -5699658231761809669L;

	private final static String SHARED_ATTRIBUTE = "shared_attribute";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session.getAttribute(SHARED_ATTRIBUTE) != null)
			session.setAttribute(SHARED_ATTRIBUTE, true);
		response.getWriter().print("my GET");
	}
}