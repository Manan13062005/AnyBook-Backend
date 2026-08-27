package com.anybook.backend.controller;

import com.anybook.backend.dto.ClientSignupRequest;
import com.anybook.backend.dto.LoginRequest;
import com.anybook.backend.dto.OwnerSignupRequest;
import com.anybook.backend.dto.UserResponse;
import com.anybook.backend.entity.User;
import com.anybook.backend.repository.UserRepository;
import com.anybook.backend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/signup/client")
    public ResponseEntity<?> signupClient(@RequestBody ClientSignupRequest request){

        if(userRepository.findByMobileNo(request.getMobileNo()).isPresent()){
            return ResponseEntity.badRequest().body("Mobile number already registered");
        }

        User user = new User();
        user.setName(request.getName());
        user.setMobileNo(request.getMobileNo());
        user.setEmail(request.getEmail());

        user.setRole(User.Role.CLIENT);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        User savedUser = userRepository.save(user);

        UserResponse response = new UserResponse(
                savedUser.getId().toString(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getMobileNo(),
                savedUser.getRole().name(),
                savedUser.getBusinessName(),
                savedUser.getBusinessDescription(),
                savedUser.getBusinessAddress()
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup/owner")
    public ResponseEntity<?> signupOwner(@RequestBody OwnerSignupRequest request){
        if(userRepository.findByEmail(request.getEmail()).isPresent() || userRepository.findByMobileNo(request.getBusinessPhoneNumber()).isPresent()){
            return ResponseEntity.badRequest().body("Email already registered");
        }
        User user = new User();
        user.setName(request.getName());
        user.setMobileNo(request.getBusinessPhoneNumber());
        user.setEmail(request.getEmail());
        user.setBusinessName(request.getBusinessName());
        user.setBusinessDescription(request.getBusinessDescription());
        user.setBusinessAddress(request.getBusinessAddress());

        user.setRole(User.Role.OWNER);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        User savedUser = userRepository.save(user);

        UserResponse response = new UserResponse(
                savedUser.getId().toString(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getMobileNo(),
                savedUser.getRole().name(),
                savedUser.getBusinessName(),
                savedUser.getBusinessDescription(),
                savedUser.getBusinessAddress()
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){

        String identifier = request.getIdentifier();
        boolean isEmail = identifier.contains("@");

        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(identifier, request.getPassword())
            );
        }catch(BadCredentialsException e){
            return ResponseEntity.status(401).body("Invalid Credentials");
        }

        User user = isEmail
                ? userRepository.findByEmail(identifier).orElseThrow()
                : userRepository.findByMobileNo(identifier).orElseThrow();

        String token = jwtUtil.generateToken(identifier);

        UserResponse userResponse = new UserResponse(
                user.getId().toString(),
                user.getName(),
                user.getEmail(),
                user.getMobileNo(),
                user.getRole().name(),
                user.getBusinessName(),
                user.getBusinessDescription(),
                user.getBusinessAddress()
        );

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("user", userResponse);

        return ResponseEntity.ok(response);
    }
}