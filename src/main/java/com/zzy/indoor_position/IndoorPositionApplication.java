package com.zzy.indoor_position;

import com.zzy.indoor_position.sql.SQLManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IndoorPositionApplication {

    public static void main(String[] args) throws Exception {
       SQLManager.init();
       SpringApplication.run(IndoorPositionApplication.class, args);
    }
}
