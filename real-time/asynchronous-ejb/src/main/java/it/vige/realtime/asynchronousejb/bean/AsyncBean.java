package it.vige.realtime.asynchronousejb.bean;

import java.util.concurrent.Future;

import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Singleton;

@Singleton
@Asynchronous
public class AsyncBean {
	
	@Asynchronous
	public void ignoreResult(int a, int b) {
		// the client doesn't care what happens here
	}

	@Asynchronous
	public Future<Integer> longProcessing(int a, int b) {
		return new AsyncResult<Integer>(a * b);
	}
}
