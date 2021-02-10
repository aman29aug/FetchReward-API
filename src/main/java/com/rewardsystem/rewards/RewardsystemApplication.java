package com.rewardsystem.rewards;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;


@SpringBootApplication(scanBasePackages = {"controller", "model", "repository"})
@EntityScan("model")

public class RewardsystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(RewardsystemApplication.class, args);
    }


}
