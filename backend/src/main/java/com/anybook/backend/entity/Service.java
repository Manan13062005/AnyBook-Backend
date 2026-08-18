package com.anybook.backend.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "services")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Service {

    private ObjectId id;
    private String ownerId;
    private String name;
    private String description;
    private double price;
    private int durationMinutes;
}