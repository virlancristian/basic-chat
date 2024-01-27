package com.example.backend;

import com.example.backend.models.database.MessageDbEntity;
import com.example.backend.models.database.UserDbEntity;
import com.example.backend.services.MessageDbService;
import com.example.backend.services.UserDbService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class BackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(UserDbService service1, MessageDbService service2) {
		return args -> {
			List<MessageDbEntity> messages = service2.getRecentMessages("varli", "domi");
			for(MessageDbEntity message:messages) {
				System.out.println(message.getMessage() + " " + message.getDate() + message.getHour());
			}
		};
	}
}
