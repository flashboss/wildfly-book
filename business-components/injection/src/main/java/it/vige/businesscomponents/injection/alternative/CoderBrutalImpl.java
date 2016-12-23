package it.vige.businesscomponents.injection.alternative;

import javax.enterprise.inject.Alternative;

import it.vige.businesscomponents.injection.decorator.Coder;

@Alternative
public class CoderBrutalImpl implements Coder {

	@Override
	public String codeString(String s, int tval) {
		return "hiiiiiiiii";
	}

}
