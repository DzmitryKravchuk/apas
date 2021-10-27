package com.belpost.apas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class ApasApplication extends SpringBootServletInitializer {

  public static void main(String[] args) {
    SpringApplication.run(ApasApplication.class, args);
  }
}