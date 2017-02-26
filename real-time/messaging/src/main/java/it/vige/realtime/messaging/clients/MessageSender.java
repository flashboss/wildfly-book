package it.vige.realtime.messaging.clients;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;

@Stateless
public class MessageSender {

	@Inject
	private JMSContext context;

	@Resource(mappedName = "java:/jms/queue/GPS")
	private Queue queue;

	public void sendMessage(String message) {
		context.createProducer().send(queue, message);
	}
}
