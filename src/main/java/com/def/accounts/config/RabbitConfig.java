package com.def.accounts.config;

import javax.sql.DataSource;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

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
	
	@Bean public DataSource dataSource() {  
		return connectionFactory().dataSource(); 
		}
	
	/*@Bean
    SendGrid sendGrid(ObjectMapper objectMapper) throws Exception {
        JsonNode credentials = objectMapper
                .readValue(System.getenv("VCAP_SERVICES"), JsonNode.class).get("sendgrid")
                .get(0).get("credentials");
        String username = credentials.get("username").asText();
        String password = credentials.get("password").asText();
        return new SendGrid(username, password);
    }*/
}
