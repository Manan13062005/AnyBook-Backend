package com.anybook.backend.dto;

import com.anybook.backend.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Optional;

@Data
@AllArgsConstructor
public class UserResponse {
    private String id;
    private String name;
    private String email;
    private String mobileNo;
    private String role;
    private String businessName;
    private String businessDescription;
    private String businessAddress;
}
