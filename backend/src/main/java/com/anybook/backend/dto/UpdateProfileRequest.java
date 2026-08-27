package com.anybook.backend.dto;

import lombok.Data;

@Data
public class UpdateProfileRequest {
    private String name;
    private String businessName;
    private String businessDescription;
    private String businessAddress;
}