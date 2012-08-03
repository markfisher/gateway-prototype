package gateways;

import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.MessagingException;
import org.springframework.integration.core.MessageHandler;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.support.MessageBuilder;

public class BlockingOutboundGateway implements MessageHandler, MessageProducer {

	private volatile MessageChannel replyChannel;

	public void setOutputChannel(MessageChannel outputChannel) {
		this.replyChannel = outputChannel;
	}

	public void handleMessage(Message<?> message) throws MessagingException {
		try {
			Thread.sleep(10000);
		}
		catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		this.replyChannel.send(MessageBuilder.withPayload("reply for: " + message.getPayload()).build());
	}

}
