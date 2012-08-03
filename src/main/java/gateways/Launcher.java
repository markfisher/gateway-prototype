package gateways;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.support.MessageBuilder;

public class Launcher {

	public static void main(String[] args) throws Exception {
		if (args.length != 2) {
			System.out.println("USAGE: Launcher [blocking | nonblocking] [batchSize]");
			System.exit(0);
		}
		String type = args[0];
		int batchSize = Integer.parseInt(args[1]);
		System.out.println("Running " + type + " with " + batchSize + " messages...");
		ApplicationContext context = new ClassPathXmlApplicationContext(type + ".xml", Launcher.class);
		MessageChannel channel = context.getBean("requestChannel", MessageChannel.class);
		for (int i = 0; i < batchSize; i++) {
			channel.send(MessageBuilder.withPayload("test." + i).build());
		}
	}

}
