package com.reachauto.hkr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by Administrator on 2017/7/12.
 */
@SpringBootApplication
@ComponentScan(value = {
        "com.reachauto.hkr.cr"
        }
)
public class CrApplication {
    public static void main(String[] args) {
        SpringApplication.run(CrApplication.class, args);
    }
}
