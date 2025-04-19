package com.in6225.IMS.controller;

import com.in6225.IMS.dto.ChangePasswordRequest;
import com.in6225.IMS.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account") // Base path for self-service account actions
public class AccountController {

    @Autowired
    private UserService userService;

    // Endpoint for the authenticated user to change their own password
    @PutMapping("/password")
    public ResponseEntity<?> changeOwnPassword(@RequestBody ChangePasswordRequest request, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body("User not authenticated."); // Should be caught by filter chain anyway
        }
        String username = authentication.getName(); // Get username from authenticated principal

        try {
            userService.changeOwnPassword(username, request.getCurrentPassword(), request.getNewPassword());
            return ResponseEntity.ok("Password updated successfully.");
        } catch (UsernameNotFoundException e) {
            // This shouldn't happen if the user is authenticated, but handle defensively
            return ResponseEntity.status(404).body("User not found.");
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(400).body(e.getMessage()); // 400 Bad Request for incorrect current password
        } catch (Exception e) { // Catch other potential errors
            // Log the exception e
            return ResponseEntity.status(500).body("An error occurred while changing the password.");
        }
    }
}