package com.in6225.IMS.init;

import com.in6225.IMS.entity.User;
import com.in6225.IMS.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByUsername("testuser").isEmpty()) {
            User user = new User();
            user.setUsername("testuser");
            user.setPassword(passwordEncoder.encode("password1234")); // plain password: "password"
            user.setRole("ROLE_USER");
            userRepository.save(user);
            System.out.println("✅ Test user created: testuser / password");
        }

        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin1234"));
            admin.setRole("ROLE_ADMIN");
            userRepository.save(admin);
            System.out.println("✅ Admin user created: admin / admin1234");
        }

        if (userRepository.findByUsername("manager").isEmpty()) {
            User admin = new User();
            admin.setUsername("manager");
            admin.setPassword(passwordEncoder.encode("manager1234"));
            admin.setRole("ROLE_MANAGER");
            userRepository.save(admin);
            System.out.println("✅ Admin user created: Manager / Manager1234");
        }
    }
}
