package com.jinhogate.web;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.jinhogate.web.model.Server;
import com.jinhogate.web.model.enums.StatusEnum;
import com.jinhogate.web.repo.ServerRepo;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class ServerApplication implements CommandLineRunner {

	@Autowired
	private ServerRepo serverRepo;

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		this.createServers();
	}

	private void createServers() {
		ServerApplication.log.info("Init servers");
		this.serverRepo.save(new Server(null, "192.168.1.160", "Ubuntu Linux", "16GB", "Personal PC",
				"http://localhost:8080/server/image/server1.png", StatusEnum.SERVER_UP));

		this.serverRepo.save(new Server(null, "192.168.1.58", "Fedora Linux", "16GB", "Dell Tower",
				"http://localhost:8080/server/image/server2.png", StatusEnum.SERVER_DOWN));

		this.serverRepo.save(new Server(null, "192.168.1.21", "MS 2008", "32GB", "Web Server",
				"http://localhost:8080/server/image/server3.png", StatusEnum.SERVER_UP));

		this.serverRepo.save(new Server(null, "192.168.1.14", "Red Hat Entreprise Linux", "64GB", "Mail Server",
				"http://localhost:8080/server/image/server4.png", StatusEnum.SERVER_DOWN));
	}

	@Bean
	CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:4200", "http://localhost:3000"));
		corsConfiguration
				.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type", "Accept",
						"Jwt-Token", "Authorization", "Origin, Accept", "X-Requested-With",
						"Access-Control-Request-Method", "Access-Control-Request-Headers"));
		corsConfiguration
				.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Jwt-Token", "Authorization",
						"Access-Control-Allow-Origin", "Access-Control-Allow-Credentials", "Filename"));
		corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
		return new CorsFilter(urlBasedCorsConfigurationSource);
	}

}
