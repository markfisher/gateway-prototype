package gateways;

import java.util.Date;

import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.MessagingException;
import org.springframework.integration.core.MessageHandler;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.util.Assert;

public class NonBlockingOutboundGateway implements MessageHandler, MessageProducer {

	private volatile TaskScheduler scheduler;

	private volatile MessageChannel replyChannel;

	public void setScheduler(TaskScheduler scheduler) {
		this.scheduler = scheduler;
	}

	public void setOutputChannel(MessageChannel outputChannel) {
		this.replyChannel = outputChannel;
	}

	public void handleMessage(final Message<?> message) throws MessagingException {
		Assert.notNull(this.scheduler, "scheduler is required");
		scheduler.schedule(new Runnable() {
			public void run() {
				replyChannel.send(MessageBuilder.withPayload("reply for: " + message.getPayload()).build());
			}
		}, new Date(System.currentTimeMillis() + 10000));
	}

}
