package com.anybook.backend.dto;

import lombok.Data;

@Data
public class ClientSignupRequest {
    private String name;
    private String mobileNo;
    private String email;
    private String password;
}
