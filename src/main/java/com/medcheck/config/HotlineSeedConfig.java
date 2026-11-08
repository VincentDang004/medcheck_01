package com.medcheck.config;

import com.medcheck.service.HotlineService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HotlineSeedConfig {

    @Bean
    public ApplicationRunner hotlineSeeder(HotlineService hotlineService) {
        return args -> hotlineService.seedDefaultsIfEmpty();
    }
}

