package com.musicapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.musicapp.repository.UserRepository;

@EnableMongoRepositories(basePackageClasses = UserRepository.class)
@Configuration
public class MongoDBConfig {

	/*
	 * @Bean CommandLineRunner commandLineRunner(UserRepository userRepository) {
	 * return strings -> { userRepository.save(new Users(1, "Peter", "Development",
	 * 3000L)); userRepository.save(new Users(2, "Sam", "Operations", 2000L)); }; }
	 */
}
