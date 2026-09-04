package com.anybook.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


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
    private String businessHours;
    private Integer businessImageCount;
}
