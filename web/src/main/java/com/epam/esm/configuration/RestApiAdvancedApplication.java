package com.epam.esm.configuration;

import com.epam.esm.config.ModelConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication(scanBasePackages = {"com.epam.esm"})
@EnableWebMvc
@EntityScan(basePackages = {"com.epam.esm.entity"})
@Import({ModelConfig.class})
public class RestApiAdvancedApplication {
    public static void main(String[] args) {
        SpringApplication.run(RestApiAdvancedApplication.class, args);
    }
}
