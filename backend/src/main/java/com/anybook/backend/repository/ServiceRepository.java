package com.anybook.backend.repository;

import com.anybook.backend.entity.Service;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ServiceRepository extends MongoRepository<Service, ObjectId> {
    List<Service> findByOwnerId(String ownerId);
}