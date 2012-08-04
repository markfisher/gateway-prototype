package gateways;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.support.MessageBuilder;

public class ExecutorBlowout {

	public static void main(String[] args) throws Exception {
		int batchSize = 100000;
		ApplicationContext context = new ClassPathXmlApplicationContext("blocking.xml", ExecutorBlowout.class);
		final MessageChannel channel = context.getBean("requestChannel", MessageChannel.class);
		Executor executor = Executors.newCachedThreadPool();
		for (int i = 0; i < batchSize; i++) {
			final int count = i;
			executor.execute(new Runnable() {
				public void run() {
					channel.send(MessageBuilder.withPayload("test." + count).build());
				}
			});
		}
	}

}
