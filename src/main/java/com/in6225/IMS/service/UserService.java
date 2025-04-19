package com.in6225.IMS.service;

import com.in6225.IMS.dto.UserDTO;
import com.in6225.IMS.entity.User;
import com.in6225.IMS.exception.ResourceNotFoundException;

import java.util.List;

public interface UserService {

    UserDTO createUser(UserDTO userDTO);

    UserDTO getUserById(Long userId);

    UserDTO getUserByUsername(String username);

    List<UserDTO> getAllUsers();

    void deleteUser(Long userId);

    User changeUserRole(String username, String newRole);

    void changeOwnPassword(String username, String currentPassword, String newPassword);


}