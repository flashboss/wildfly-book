package it.vige.webprogramming.javaserverfaces.ui.view;

import static java.util.ResourceBundle.getBundle;
import static javax.faces.context.FacesContext.getCurrentInstance;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

public enum SummaryMode {
	BLOCK_TOPICS_MODE_HOT_TOPICS, BLOCK_TOPICS_MODE_HOTTEST_TOPICS, BLOCK_TOPICS_MODE_LATEST_POSTS, BLOCK_TOPICS_MODE_MOST_VIEWED;

	public String toString() {
		FacesContext context = getCurrentInstance();
		ResourceBundle res;
		if (context != null) {
			Locale locale = context.getViewRoot().getLocale();
			res = getBundle("ResourceJSF", locale);
		} else
			res = getBundle("ResourceJSF");
		return res.getString(name());
	}
}
