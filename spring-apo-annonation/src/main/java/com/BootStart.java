package com;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BootStart {
	public static void main(String[] args) {
		System.setProperty("app.path",".");
		SpringApplication.run(BootStart.class);
		System.out.println("start success");
	}
}
