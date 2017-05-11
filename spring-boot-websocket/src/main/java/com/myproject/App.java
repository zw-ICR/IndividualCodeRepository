package com.myproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * 程序入口
 *
 */
@SpringBootApplication
@ServletComponentScan
public class App {
    public static void main( String[] args ) {
    	if((System.getProperty("app.path") == null) || System.getProperty("app.path").equals("")){
			System.setProperty("app.path",System.getProperty("user.dir"));
		}
		SpringApplication.run(App.class, args);
    }
}
