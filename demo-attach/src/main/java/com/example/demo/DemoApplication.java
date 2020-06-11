package com.example.demo;

import com.sun.tools.attach.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.List;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) throws IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException {


        SpringApplication.run(DemoApplication.class, args);

        //--------------PreMain Start-------------
        System.out.println("premain执行成功了");
        //--------------PreMain Emd-------------



    }

}
