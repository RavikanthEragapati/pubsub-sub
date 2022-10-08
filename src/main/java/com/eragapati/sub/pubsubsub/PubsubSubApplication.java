package com.eragapati.sub.pubsubsub;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.api.gax.core.CredentialsProvider;
import com.google.cloud.NoCredentials;
import com.google.cloud.spring.pubsub.core.subscriber.PubSubSubscriberTemplate;
import com.google.cloud.spring.pubsub.support.converter.JacksonPubSubMessageConverter;
import com.google.cloud.spring.pubsub.support.converter.PubSubMessageConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.io.IOException;

@SpringBootApplication
@RequiredArgsConstructor
public class PubsubSubApplication {


	public static void main(String[] args) {
		SpringApplication.run(PubsubSubApplication.class, args);
	}

	@Bean
	@Primary
	public CredentialsProvider googleCredentials() throws IOException {
		return NoCredentials::getInstance;
	}
	@Bean
	@Primary
	public PubSubMessageConverter pubSubMessageConverter(ObjectMapper objectMapper) {
		return new JacksonPubSubMessageConverter(objectMapper);
	}


	@Bean
	ApplicationRunner subscribeRunner(PubSubSubscriberTemplate subscriberTemplate) {
		return (args) -> {
			subscriberTemplate.subscribe("ravi-sub", messageConsumer -> {
				System.out.println(messageConsumer.getPubsubMessage().getMessageId());
				System.out.println(messageConsumer.getPubsubMessage().getData().toStringUtf8());
				messageConsumer.ack();
			});
		};
	}

}
