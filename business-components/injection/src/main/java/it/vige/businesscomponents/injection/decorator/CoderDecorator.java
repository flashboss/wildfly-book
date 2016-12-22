package it.vige.businesscomponents.injection.decorator;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.inject.Any;
import javax.inject.Inject;

@Decorator
public abstract class CoderDecorator implements Coder {

    @Inject
    @Delegate
    @Any
    private Coder coder;

    @Override
    public String codeString(String s, int tval) {
        int len = s.length();

        return "\"" + s + "\" becomes " + "\"" + coder.codeString(s, tval) 
                + "\", " + len + " characters in length";
    }
}
