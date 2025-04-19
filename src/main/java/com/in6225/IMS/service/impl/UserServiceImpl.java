package com.in6225.IMS.service.impl;

import com.in6225.IMS.dto.UserDTO;
import com.in6225.IMS.entity.User;
import com.in6225.IMS.exception.ResourceNotFoundException;
import com.in6225.IMS.mapper.UserMapper;
import com.in6225.IMS.repository.UserRepository;
import com.in6225.IMS.service.UserService;
import lombok.RequiredArgsConstructor; // Lombok annotation for constructor injection
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils; // For checking empty strings

import java.util.List;

@Service
@RequiredArgsConstructor // Creates constructor with final fields (Autowired alternative)
@Transactional // Add transaction management to service methods
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder; // Inject PasswordEncoder

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        // Optional: Check if username already exists
        userRepository.findByUsername(userDTO.getUsername()).ifPresent(u -> {
            throw new IllegalArgumentException("Username already exists: " + userDTO.getUsername());
        });

        User user = userMapper.toEntity(userDTO);

        // **Crucial: Encode the password before saving**
        if (!StringUtils.hasText(userDTO.getPassword())) {
            throw new IllegalArgumentException("Password cannot be empty for new user");
        }
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        User savedUser = userRepository.save(user); // @PrePersist in BaseEntity will be triggered
        return userMapper.toDto(savedUser); // Map back to DTO (password will be ignored by mapper)
    }

    @Override
    @Transactional(readOnly = true) // Read-only transaction for queries
    public UserDTO getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        return userMapper.toDto(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        return userMapper.toDto(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.toDtoList(users);
    }

    @Transactional // Ensure atomicity
    public User changeUserRole(String username, String newRole) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        user.setRole(newRole);
        return userRepository.save(user);
    }

    @Transactional // Inherits default read-write transactionality
    public void changeOwnPassword(String username, String currentPassword, String newPassword) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    // Should be rare if called from authenticated context
                    return new UsernameNotFoundException("User not found with username: " + username);
                });

        // 1. Verify current password
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new BadCredentialsException("Incorrect current password");
        }

        // Add more validation for the new password complexity if needed
        if (!StringUtils.hasText(newPassword) || newPassword.trim().length() < 6) { // Example minimum length
            throw new IllegalArgumentException("New password must be at least 6 characters long.");
        }
        if (currentPassword.equals(newPassword)) {
            throw new IllegalArgumentException("New password cannot be the same as the current password.");
        }

        // 2. Encode and set the new password
        user.setPassword(passwordEncoder.encode(newPassword));

        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User", "id", userId);
        }
        userRepository.deleteById(userId);
    }
}