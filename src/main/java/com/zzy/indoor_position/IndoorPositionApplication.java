package com.zzy.indoor_position;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;

@SpringBootApplication
public class IndoorPositionApplication {

    public static void main(String[] args) throws Exception {
       SQLConnection.init();
       SpringApplication.run(IndoorPositionApplication.class, args);
    }
}
