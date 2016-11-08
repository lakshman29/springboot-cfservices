package com.def.accounts.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.cloud.config.java.AbstractCloudConfig;

@Configuration
@ComponentScan
@Profile("cloud")
public class RabbitConfig extends AbstractCloudConfig {

	@Bean
	public RabbitTemplate rabbitTemplate() {
		
		return new RabbitTemplate(connectionFactory().rabbitConnectionFactory());
	}
	
	@Bean
	public RabbitAdmin testQueue() {
		RabbitAdmin admin = new RabbitAdmin(
				connectionFactory().rabbitConnectionFactory());
		admin.declareQueue(new Queue("test",true));
		//return new Queue("test"); 
		return admin;
}
}