package it.vige.webprogramming.servletjsp.nonblocking;

import static java.util.Arrays.fill;
import static java.util.logging.Level.SEVERE;
import static java.util.logging.Logger.getLogger;

import java.io.IOException;

import javax.servlet.AsyncContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;

public class WritingListener implements WriteListener {

	private ServletOutputStream output = null;
	private AsyncContext context = null;

	public WritingListener(ServletOutputStream out, AsyncContext ac) {
		this.output = out;
		this.context = ac;
	}

	@Override
	public void onWritePossible() {
		if (output.isReady()) {
			try {
				byte[] b = new byte[100];
				fill(b, 0, 100, (byte) 'a');
				output.write(b);
			} catch (IOException ex) {
				getLogger(WritingListener.class.getName()).log(SEVERE, null, ex);
			}
		}
	}

	@Override
	public void onError(Throwable t) {
		t.printStackTrace();
		context.complete();
	}
}