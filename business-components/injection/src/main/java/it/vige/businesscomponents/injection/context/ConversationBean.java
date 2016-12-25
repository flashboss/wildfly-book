package it.vige.businesscomponents.injection.context;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;

@ConversationScoped
public class ConversationBean implements Serializable {

	private static final long serialVersionUID = -5575830634866764183L;

	@Inject
	private Conversation conversation;

	private int counter;

	@PostConstruct
	public void init() {
		counter = 0;
	}

	public void initConversation() {
		conversation.begin();
	}

	public void increment() {
		counter++;
	}

	public String handleFirstStepSubmit() {
		return "step2?faces-redirect=true";
	}

	public String endConversation() {
		if (!conversation.isTransient()) {
			conversation.end();
		}
		return "step1?faces-redirect=true";
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public Conversation getConversation() {
		return conversation;
	}

}
