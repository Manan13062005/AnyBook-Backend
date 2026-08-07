package com.anybook.backend.dto;

import lombok.Data;

@Data
public class OwnerSignupRequest {
    private String name;
    private String businessName;
    private String businessPhoneNumber;
    private String email;
    private String password;
}
