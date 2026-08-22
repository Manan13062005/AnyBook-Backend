package com.anybook.backend.repository;

import com.anybook.backend.entity.Appointment;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AppointmentRepository extends MongoRepository<Appointment, ObjectId> {
    List<Appointment> findByClientId(String clientId);
    List<Appointment> findByOwnerId(String ownerId);
}