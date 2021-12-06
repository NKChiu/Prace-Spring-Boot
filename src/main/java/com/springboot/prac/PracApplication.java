package com.springboot.prac;

import com.springboot.prac.config.TestDataSourceConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Arrays;
import java.util.Properties;

@Slf4j
@SpringBootApplication
public class PracApplication implements CommandLineRunner {


	@Autowired
	private Environment env;

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(PracApplication.class);
		app.run(args);
	}


	@Override
	public void run(String... args) throws Exception {
		log.info("Active profiles: " + Arrays.toString(env.getActiveProfiles()));
	}

}