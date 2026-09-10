package com.anybook.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import tools.jackson.databind.annotation.JsonSerialize;
import tools.jackson.databind.ser.std.ToStringSerializer;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;

    private String name;
    private String email;
    private String password;
    private String mobileNo;
    private Role role;

    private String businessName;
    private String businessDescription;
    private String businessAddress;
    private String businessHours;

    private String profileImageId;

    private Integer businessImageCount;

    private List<String> businessImageIds = new ArrayList<>();

    public enum Role {
        CLIENT, OWNER
    }
}