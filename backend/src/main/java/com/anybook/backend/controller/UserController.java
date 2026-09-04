package com.anybook.backend.controller;

import com.anybook.backend.dto.UpdateProfileRequest;
import com.anybook.backend.dto.UserResponse;
import com.anybook.backend.entity.User;
import com.anybook.backend.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody UpdateProfileRequest request) {
        String loggedInIdentifier = org.springframework.security.core.context.SecurityContextHolder
                .getContext().getAuthentication().getName();

        User loggedInUser = userRepository.findByEmail(loggedInIdentifier)
                .or(() -> userRepository.findByMobileNo(loggedInIdentifier))
                .orElseThrow();

        if (!loggedInUser.getId().toString().equals(id)) {
            return ResponseEntity.status(403).body("You can only edit your own profile");
        }

        User user = userRepository.findById(new ObjectId(id))
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (request.getName() != null) user.setName(request.getName());
        if (request.getEmail() != null) user.setEmail(request.getEmail());
        if (request.getMobileNo() != null) user.setMobileNo(request.getMobileNo());
        if (request.getBusinessName() != null) user.setBusinessName(request.getBusinessName());
        if (request.getBusinessDescription() != null) user.setBusinessDescription(request.getBusinessDescription());
        if (request.getBusinessAddress() != null) user.setBusinessAddress(request.getBusinessAddress());
        if (request.getBusinessHours() != null) user.setBusinessHours(request.getBusinessHours());
        if (request.getBusinessImageCount() != null) user.setBusinessImageCount(request.getBusinessImageCount());


        User saved = userRepository.save(user);

        UserResponse response = new UserResponse(
                saved.getId().toString(),
                saved.getName(),
                saved.getEmail(),
                saved.getMobileNo(),
                saved.getRole().toString(),
                saved.getBusinessName(),
                saved.getBusinessDescription(),
                saved.getBusinessAddress(),
                saved.getBusinessHours(),
                saved.getBusinessImageCount()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id) {

        User user = userRepository.findById(new ObjectId(id))
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserResponse response = new UserResponse(
                user.getId().toString(),
                user.getName(),
                user.getEmail(),
                user.getMobileNo(),
                user.getRole().toString(),
                user.getBusinessName(),
                user.getBusinessDescription(),
                user.getBusinessAddress(),
                user.getBusinessHours(),
                user.getBusinessImageCount()
        );

        return ResponseEntity.ok(response);
    }
}