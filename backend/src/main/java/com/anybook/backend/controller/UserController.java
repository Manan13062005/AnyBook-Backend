package com.anybook.backend.controller;

import com.anybook.backend.dto.UserResponse;
import com.anybook.backend.entity.User;
import com.anybook.backend.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.anybook.backend.dto.UpdateProfileRequest;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
        if (request.getBusinessName() != null) user.setBusinessName(request.getBusinessName());
        if (request.getBusinessDescription() != null) user.setBusinessDescription(request.getBusinessDescription());
        if (request.getBusinessAddress() != null) user.setBusinessAddress(request.getBusinessAddress());

        User saved = userRepository.save(user);

        UserResponse response = new UserResponse(
                saved.getId().toString(),
                saved.getName(),
                saved.getEmail(),
                saved.getMobileNo(),
                saved.getRole().toString(),
                saved.getBusinessName(),
                saved.getBusinessDescription(),
                saved.getBusinessAddress()
        );
        return ResponseEntity.ok(response);
    }

}