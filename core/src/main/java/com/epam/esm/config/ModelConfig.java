package com.epam.esm.config;

import com.epam.esm.utils.ApplicationValidator;
import com.epam.esm.utils.ApplicationValidatorImpl;
import com.epam.esm.utils.PaginationProvider;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Obidjon Eshmamatov
 * @project rest_api_advanced_2
 * @created 31/05/2022 - 4:46 PM
 */

@Configuration
@ComponentScan(basePackages = {"com.epam.esm"})
@EnableTransactionManagement
public class ModelConfig {
    @Bean
    public ApplicationValidator applicationValidator() {
        return new ApplicationValidatorImpl();
    }

    @Bean
    public PaginationProvider paginationProvider() {
        return new PaginationProvider(applicationValidator());
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
