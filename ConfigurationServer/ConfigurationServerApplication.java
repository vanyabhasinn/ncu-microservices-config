package com.ncu.library.ConfigurationServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ConfigurationServerApplication {


	public static void main(String[] args) {
		String mode = System.getenv("CONFIG_MODE");

		 SpringApplication app = new SpringApplication(ConfigurationServerApplication.class);
        
        if ("native".equalsIgnoreCase(mode)) {
            app.setAdditionalProfiles("native");
        } else {
            app.setAdditionalProfiles("git");
        }
        app.run(args);
	}

}
