package com.anybook.backend.controller;

import com.anybook.backend.dto.UserResponse;
import com.anybook.backend.entity.User;
import com.anybook.backend.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id) {
        User user = userRepository.findById(new ObjectId(id))
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserResponse response = new UserResponse(
                user.getId().toString(),
                user.getName(),
                user.getEmail(),
                user.getMobileNo(),
                user.getRole().toString()
        );
        return ResponseEntity.ok(response);
    }
}