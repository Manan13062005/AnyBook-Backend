package com.anybook.backend.controller;

import com.anybook.backend.entity.Service;
import com.anybook.backend.entity.User;
import com.anybook.backend.repository.ServiceRepository;
import com.anybook.backend.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/services")
public class ServiceController {

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private UserRepository userRepository;

    private User getLoggedInOwner() {
        String identifier = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(identifier)
                .or(() -> userRepository.findByMobileNo(identifier))
                .orElseThrow();
    }

    @PostMapping
    public ResponseEntity<?> createService(@RequestBody Service serviceRequest) {
        User owner = getLoggedInOwner();
        serviceRequest.setOwnerId(owner.getId().toString());
        Service saved = serviceRepository.save(serviceRequest);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<?> getServicesByOwner(@PathVariable String ownerId) {
        List<Service> services = serviceRepository.findByOwnerId(ownerId);
        return ResponseEntity.ok(services);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateService(@PathVariable String id, @RequestBody Service serviceRequest) {
        User owner = getLoggedInOwner();

        Service existing = serviceRepository.findById(new ObjectId(id))
                .orElseThrow(() -> new RuntimeException("Service not found"));

        if (!existing.getOwnerId().equals(owner.getId().toString())) {
            return ResponseEntity.status(403).body("You do not own this service");
        }

        existing.setName(serviceRequest.getName());
        existing.setDescription(serviceRequest.getDescription());
        existing.setPrice(serviceRequest.getPrice());
        existing.setDurationMinutes(serviceRequest.getDurationMinutes());

        Service updated = serviceRepository.save(existing);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteService(@PathVariable String id) {
        User owner = getLoggedInOwner();

        Service existing = serviceRepository.findById(new ObjectId(id))
                .orElseThrow(() -> new RuntimeException("Service not found"));

        if (!existing.getOwnerId().equals(owner.getId().toString())) {
            return ResponseEntity.status(403).body("You do not own this service");
        }

        serviceRepository.deleteById(new ObjectId(id));
        return ResponseEntity.ok("Service deleted successfully");
    }

}