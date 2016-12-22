package it.vige.businesscomponents.injection.decorator;

import javax.inject.Named;

@Named
public class CoderImpl implements Coder {

	@Override
	public String codeString(String s, int tval) {
		return "hi";
	}

}
