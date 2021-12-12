package com.springboot.prac;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.*;

import java.net.InetAddress;
import java.util.Arrays;

@Slf4j
@SpringBootApplication
public class PracApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(PracApplication.class);
		Environment env = app.run(args).getEnvironment();
		appInfo(env);
	}

	public static void appInfo(Environment env){
		String appName = env.getProperty("spring.application.name");
		String activeProfiles = Arrays.toString(env.getActiveProfiles());
		String http = "http://";
		String localDomain = "localhost:";
		String ip = getIpAddress();
		String port = env.getProperty("server.port");
		String path = env.getProperty("server.servlet.context-path");

		log.info("\n============spring boot start=======================\n\t" +
				"Application " + appName + " is running! \n\t" +
				"Active profiles: " + activeProfiles + "\n\t" +
				"Access URLs:\n\t" +
				"Local: \t\t\t" + http + localDomain + port + path + "\n\t" +
				"External: \t\t" + http + ip + ":" + port + path + "\n\t" +
				"swagger-ui: \t" + http + localDomain + port + path + "/swagger-ui.html" + "\n" +
				"============spring boot end============================");

	}


	private static String getIpAddress(){
		try {
			return InetAddress.getLocalHost().getHostAddress();
		}catch (Exception e){
			log.error("", e);
			return null;
		}
	}


}