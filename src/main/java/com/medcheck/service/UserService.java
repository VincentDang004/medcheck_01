package com.medcheck.service;

import com.medcheck.model.User;
import com.medcheck.repository.UserRepository;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");
        user.setBlocked(false);
        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAllByOrderByBlockedDescUsernameAsc();
    }

    public long countBlockedUsers() {
        return userRepository.findAll().stream().filter(User::isBlocked).count();
    }

    public void blockUser(Long id) {
        userRepository.findById(id).ifPresent(user -> {
            user.setBlocked(true);
            userRepository.save(user);
        });
    }

    public void unblockUser(Long id) {
        userRepository.findById(id).ifPresent(user -> {
            user.setBlocked(false);
            user.setSpamStrike(0);
            userRepository.save(user);
        });
    }

    public void createAdminIfNotExist() {
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin123");
            admin.setPassword(passwordEncoder.encode("kakaka123"));
            admin.setRole("ADMIN");
            admin.setBlocked(false);
            userRepository.save(admin);
        }
    }
}
