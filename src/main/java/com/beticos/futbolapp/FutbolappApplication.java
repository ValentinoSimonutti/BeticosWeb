package com.beticos.futbolapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.ZoneId;
import java.util.TimeZone;

@SpringBootApplication
public class FutbolappApplication {

	public static void main(String[] args) {
		System.out.println("TimeZone: " + TimeZone.getDefault().getID());
		System.out.println("ZoneId  : " + ZoneId.systemDefault());

		SpringApplication.run(FutbolappApplication.class, args);
	}

}
