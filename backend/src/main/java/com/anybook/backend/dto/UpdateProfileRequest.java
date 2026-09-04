package com.anybook.backend.dto;

import lombok.Data;

@Data
public class UpdateProfileRequest {
    private String name;
    private String email;
    private String mobileNo;
    private String businessName;
    private String businessDescription;
    private String businessAddress;
    private String businessHours;
    private Integer businessImageCount;
}