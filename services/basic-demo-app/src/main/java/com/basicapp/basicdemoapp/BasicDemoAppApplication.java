package com.basicapp.basicdemoapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.bigid.appinfra", "com.basicapp.basicdemoapp"})
public class BasicDemoAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasicDemoAppApplication.class, args);
	}

}