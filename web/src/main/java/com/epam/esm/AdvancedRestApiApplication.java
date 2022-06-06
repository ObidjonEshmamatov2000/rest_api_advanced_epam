package com.epam.esm;

import com.epam.esm.assembler.AppUserModelAssembler;
import com.epam.esm.assembler.GiftCertificateModelAssembler;
import com.epam.esm.assembler.OrderModelAssembler;
import com.epam.esm.assembler.TagModelAssembler;
import com.epam.esm.config.ModelConfig;
import com.epam.esm.controller.AppUserController;
import com.epam.esm.controller.GiftCertificateController;
import com.epam.esm.controller.OrderController;
import com.epam.esm.controller.TagController;
import com.epam.esm.dto.model.AppUserModel;
import com.epam.esm.dto.model.GiftCertificateModel;
import com.epam.esm.dto.model.OrderModel;
import com.epam.esm.dto.model.TagModel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
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
    @Bean
    public AppUserModelAssembler appUserModelAssembler() {
        return new AppUserModelAssembler(AppUserController.class, AppUserModel.class);
    }

    @Bean
    public GiftCertificateModelAssembler giftCertificateModelAssembler() {
        return new GiftCertificateModelAssembler(GiftCertificateController.class, GiftCertificateModel.class);
    }

    @Bean
    public OrderModelAssembler orderModelAssembler() {
        return new OrderModelAssembler(OrderController.class, OrderModel.class);
    }

    @Bean
    public TagModelAssembler tagModelAssembler() {
        return new TagModelAssembler(TagController.class, TagModel.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(AdvancedRestApiApplication.class, args);
    }
}
