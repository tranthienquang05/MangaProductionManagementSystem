package com.manga.mangaproductionmanagement.config;

import com.manga.mangaproductionmanagement.entity.User;
import com.manga.mangaproductionmanagement.entity.UserStatus;
import com.manga.mangaproductionmanagement.module.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile("!prod")
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            User u = User.builder()
                    .fullName("testuser")
                    .email("test@example.com")
                    .passwordHash(passwordEncoder.encode("password"))
                    .status(UserStatus.Active)
                    .build();
            userRepository.save(u);
            System.out.println("Inserted test user: test@example.com / password");
        }
    }
}
