package com.anybook.backend.controller;

import com.anybook.backend.entity.User;
import com.anybook.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/owners")
public class OwnerController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<?> getAllOwners() {
        List<User> owners = userRepository.findAll().stream()
                .filter(u -> u.getRole() == User.Role.OWNER)
                .toList();
        return ResponseEntity.ok(owners);
    }
}