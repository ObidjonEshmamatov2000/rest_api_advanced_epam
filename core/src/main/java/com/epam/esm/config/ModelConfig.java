package com.epam.esm.config;

import com.epam.esm.utils.ApplicationValidator;
import com.epam.esm.utils.ApplicationValidatorImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages = {"com.epam.esm"})
@EnableTransactionManagement
public class ModelConfig {

    @Bean
    public ApplicationValidator applicationValidator() {
        return new ApplicationValidatorImpl();
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
