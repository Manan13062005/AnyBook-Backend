package com.anybook.backend.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private ObjectId id;
    private String name;
    private String email;
    private String password;
    private String mobileNo;
    private Role role;

    public enum Role {
        CLIENT, OWNER
    }

}
