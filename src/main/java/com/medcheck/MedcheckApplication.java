package com.medcheck;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.medcheck.model.Medicine;
import com.medcheck.repository.MedicineRepository;

@SpringBootApplication
public class MedcheckApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedcheckApplication.class, args);
    }

}   
