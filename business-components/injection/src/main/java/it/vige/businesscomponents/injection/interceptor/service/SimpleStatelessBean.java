package it.vige.businesscomponents.injection.interceptor.service;

import javax.interceptor.ExcludeDefaultInterceptors;

public class SimpleStatelessBean {

	private String text;

	public String getText() {
		return text;
	}

	@ExcludeDefaultInterceptors
	public void setText(String text) {
		this.text = text;
	}
}
