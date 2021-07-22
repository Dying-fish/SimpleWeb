package com.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import com.example.domain.Authority;
import com.example.domain.User;
import com.example.repository.AuthRepository;
import com.example.repository.UserRepository;

@SpringBootApplication
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

    @Bean
    public CommandLineRunner dataLoader(UserRepository userRepository, AuthRepository authorityRepository) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                // Create authorities if not exist.
                Authority founder = getOrCreateAuthority("Founder", 9, authorityRepository);
                getOrCreateAuthority("Admin",5, authorityRepository);
                getOrCreateAuthority("User", 1, authorityRepository);
                // Create an Admin if not exists.
                if (userRepository.findByUsername("DyingFish") == null) {
                    User admin = new User(
                            "DyingFish",
                            "18307110072@fudan.edu.cn",
                            "Dying_fish233",
                            founder
                    );
                    admin.setCoins(9999);
                    userRepository.save(admin);
                }
            }

            private Authority getOrCreateAuthority(String authorityText, int accessLevel, AuthRepository authorityRepository) {
                Authority authority = authorityRepository.findByAuthority(authorityText);
                if (authority == null) {
                    authority = new Authority(authorityText, accessLevel);
                    authorityRepository.save(authority);
                }
                return authority;
            }
        };
    }
}
