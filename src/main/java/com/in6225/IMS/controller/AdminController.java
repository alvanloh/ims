package com.in6225.IMS.controller;

import com.in6225.IMS.dto.ChangeRoleRequest;
import com.in6225.IMS.dto.UserDTO;
import com.in6225.IMS.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users") // Base path for admin user operations
public class AdminController {

    @Autowired
    private UserService userService;

    // POST /api/admin/users - Create a new user
    @PostMapping
    public ResponseEntity<UserDTO> createUser(/*@Valid*/ @RequestBody UserDTO userDTO) {
        UserDTO createdUser = userService.createUser(userDTO);
        // Return 201 Created status with the created user DTO (password excluded)
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    // GET /api/admin/users/{id} - Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO userDTO = userService.getUserById(id);
        return ResponseEntity.ok(userDTO); // Return 200 OK
    }

    // GET /api/admin/users/username/{username} - Get user by username
    @GetMapping("/username/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
        UserDTO userDTO = userService.getUserByUsername(username);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // DELETE /api/admin/users/{id} - Delete a user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build(); // Return 204 No Content
    }

    // Endpoint for Admin to change a user's role
    @PutMapping("/username/{username}/role")
    public ResponseEntity<?> changeUserRole(@PathVariable String username, @RequestBody ChangeRoleRequest request) {
        try {
            // TODO: Add validation to ensure the role string is valid (e.g., "USER", "MANAGER", "ADMIN")
            userService.changeUserRole(username, request.getRole());
            return ResponseEntity.ok("Role updated successfully for user: " + username);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) { // Catch other potential errors
            // Log the exception e
            return ResponseEntity.status(500).body("An error occurred while changing the role.");
        }
    }
}