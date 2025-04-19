package com.in6225.IMS.mapper;

import com.in6225.IMS.dto.UserDTO;
import com.in6225.IMS.entity.User;
import org.springframework.stereotype.Component; // Import Spring's @Component

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Manual implementation of mapping between User entity and UserDTO.
 * Replaces the MapStruct generated mapper.
 */
@Component // Make this class a Spring managed bean
public class UserMapper {

    /**
     * Maps a User entity to a UserDTO.
     * The password field is ignored for security reasons.
     *
     * @param user The User entity.
     * @return The corresponding UserDTO, or null if the input is null.
     */
    public UserDTO toDto(User user) {
        if (user == null) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        // Map common fields
        userDTO.setId(user.getId()); // Assuming User entity has getId()
        userDTO.setUsername(user.getUsername());
        userDTO.setRole(user.getRole());
        // userDTO.setPassword(null); // Explicitly null or just don't set it - password ignored as requested

        // Map BaseEntity fields if needed in DTO (usually not needed, but shown for completeness)
        userDTO.setCreatedDate(user.getCreatedDate());
        userDTO.setUpdatedDate(user.getUpdatedDate());
        // userDTO.setCreatedBy(user.getCreatedBy());
        // userDTO.setUpdatedBy(user.getUpdatedBy());

        return userDTO;
    }

    /**
     * Maps a list of User entities to a list of UserDTOs.
     *
     * @param users The list of User entities.
     * @return A list of corresponding UserDTOs, or an empty list if the input is null or empty.
     */
    public List<UserDTO> toDtoList(List<User> users) {
        if (users == null || users.isEmpty()) {
            return new ArrayList<>(); // Return empty list instead of null
        }
        return users.stream()
                .map(this::toDto) // Use the toDto method for each user
                .collect(Collectors.toList());
    }

    /**
     * Maps a UserDTO to a new User entity.
     * BaseEntity fields (id, createdDate, updatedDate, createdBy, updatedBy) are ignored
     * as they are typically managed by the persistence layer or auditing.
     * The password *is* mapped from the DTO (e.g., for user creation).
     *
     * @param userDTO The UserDTO.
     * @return A new User entity, or null if the input is null.
     */
    public User toEntity(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }

        User user = new User();

        // Map fields from DTO to Entity
        user.setUsername(userDTO.getUsername());
        user.setRole(userDTO.getRole());
        user.setPassword(userDTO.getPassword()); // Map password for creation

        // ID and BaseEntity fields are intentionally not set from DTO
        // user.setId(null); // ID should be generated by persistence layer
        // user.setCreatedDate(null); // Should be handled by @PrePersist or DB default
        // user.setUpdatedDate(null); // Should be handled by @PrePersist/@PreUpdate or DB default
        // user.setCreatedBy(null);   // Should be set by auditing logic
        // user.setUpdatedBy(null);   // Should be set by auditing logic

        return user;
    }
}