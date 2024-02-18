package com.example.backend;

import com.example.backend.services.database.ConversationDbSevice;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(ConversationDbSevice service) {
		return args -> {
			System.out.println(service.getConversationByRecipients("varli", "domi"));
		};
	}
}
