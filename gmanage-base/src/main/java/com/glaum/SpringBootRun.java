package com.glaum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
@ComponentScan(basePackages = "com")
@EnableJpaAuditing
public class SpringBootRun  {

    public static Logger logger = LoggerFactory.getLogger(SpringBootRun.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringBootRun.class, args);
    }

}
