package it.vige.realtime.messaging.clients;

public interface Constants {

	String QUEUE_NAME = "gps_coordinates";
	String QUEUE_LOOKUP = "java:/jms/queue/GPS";

	String TOPIC_NAME = "bus_stops";
	String TOPIC_LOOKUP = "java:/jms/topic/BUS";
}
