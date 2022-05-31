package com.epam.esm;

import com.epam.esm.config.ModelConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author Obidjon Eshmamatov
 * @project rest_api_advanced_2
 * @created 31/05/2022 - 4:46 PM
 */

@SpringBootApplication(scanBasePackages = {"com.epam.esm"})
@EnableWebMvc
@EntityScan(basePackages = {"com.epam.esm.entity"})
@Import({ModelConfig.class})
public class AdvancedRestApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdvancedRestApiApplication.class, args);
    }
}
