package com.igazl.learning.spring.security.oauthlearning;

import com.igazl.learning.spring.security.oauthlearning.security.JwtAuthConverterProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(JwtAuthConverterProperties.class)
public class OauthLearningApplication {

	public static void main(String[] args) {
		SpringApplication.run(OauthLearningApplication.class, args);
	}

}
