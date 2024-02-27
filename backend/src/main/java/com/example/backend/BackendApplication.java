package com.example.backend;

import com.example.backend.services.database.ConversationDbSevice;
import com.example.backend.services.database.MessageDbService;
import com.example.backend.services.storage.ImageStorageService;
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
	public CommandLineRunner demo(MessageDbService service, ImageStorageService imageStorageService) {
		return args -> {
			System.out.println(service.getMessageAlikeById(2L, "%bomb%"));
			System.out.println(service.getMessageAlikeByRecipients("varli", "domi", "%hello%"));
		};
	}
}
