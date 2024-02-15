package com.example.tourmanagement;

import com.example.tourmanagement.model.entity.TourDetails;
import com.example.tourmanagement.repository.clients.UserRepository;
import com.example.tourmanagement.repository.hotel.HotelRepository;
import com.example.tourmanagement.repository.tour.TourDetailsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TourismManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(TourismManagementApplication.class, args);
    }


    @Bean
    CommandLineRunner commandLineRunner(UserRepository hotelRepository) {

        return args -> {

            System.err.println(hotelRepository.findById(1L).orElseThrow().getImage());

        };
    }
}
