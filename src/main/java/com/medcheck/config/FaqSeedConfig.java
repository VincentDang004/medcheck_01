package com.medcheck.config;

import com.medcheck.service.FaqService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FaqSeedConfig {

    @Bean
    public ApplicationRunner faqSeeder(FaqService faqService) {
        return args -> faqService.seedDefaultsIfEmpty();
    }
}

