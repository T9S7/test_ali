package com.armsmart.jupiter.bs.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 应用启动入口
 *
 * @author wei.lin
 * @date 2021/1/2 0002
 **/
@ComponentScan("com.armsmart")
@SpringBootApplication(scanBasePackages = {"com.armsmart"})
@EnableScheduling
public class JupiterApplication {

    public static void main(String[] args) {
        SpringApplication.run(JupiterApplication.class, args);
    }
}
