package io.github.jonathan.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import io.github.jonathan.domain.Usuario;
import io.github.jonathan.repository.UserRepository;

@Configuration
@Profile("local")
public class LocalConfig {

	@Autowired
	private UserRepository repository;
	
	@Bean
	public void startDB() {
		Usuario u1 = new Usuario(null, "jony", "jony@email.com", "1223");
		Usuario u2 = new Usuario(null, "aha", "aha@email.com", "1223");
		repository.saveAll(List.of(u1,u2));
	}
	
}
