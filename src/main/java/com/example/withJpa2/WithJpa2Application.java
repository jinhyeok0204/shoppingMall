package com.example.withJpa2;

import com.fasterxml.jackson.datatype.hibernate5.jakarta.Hibernate5JakartaModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WithJpa2Application {

	public static void main(String[] args) {
		SpringApplication.run(WithJpa2Application.class, args);
	}

	@Bean
	Hibernate5JakartaModule hibernate5JakartaModule(){
		return new Hibernate5JakartaModule();
	}
}
