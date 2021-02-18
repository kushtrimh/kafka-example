package com.kushtrimh.kafkaexampleproducer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class KafkaExampleProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaExampleProducerApplication.class, args);
	}
}
