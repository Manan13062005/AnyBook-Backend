package com.anybook.backend.controller;

import com.anybook.backend.entity.Appointment;
import com.anybook.backend.entity.Service;
import com.anybook.backend.entity.User;
import com.anybook.backend.repository.AppointmentRepository;
import com.anybook.backend.repository.ServiceRepository;
import com.anybook.backend.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private UserRepository userRepository;

    private User getLoggedInUser() {
        String identifier = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(identifier)
                .or(() -> userRepository.findByMobileNo(identifier))
                .orElseThrow();
    }

    @PostMapping
    public ResponseEntity<?> createAppointment(@RequestBody Appointment request) {
        User client = getLoggedInUser();

        Service service = serviceRepository.findById(new ObjectId(request.getServiceId()))
                .orElseThrow(() -> new RuntimeException("Service not found"));

        Appointment appointment = new Appointment();
        appointment.setClientId(client.getId().toString());
        appointment.setOwnerId(service.getOwnerId());
        appointment.setServiceId(service.getId().toString());
        appointment.setDateTime(request.getDateTime());
        appointment.setStatus(Appointment.Status.PENDING);
        appointment.setCreatedAt(LocalDateTime.now());

        Appointment saved = appointmentRepository.save(appointment);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<?> getAppointmentsByClient(@PathVariable String clientId) {
        return ResponseEntity.ok(appointmentRepository.findByClientId(clientId));
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<?> getAppointmentsByOwner(@PathVariable String ownerId) {
        return ResponseEntity.ok(appointmentRepository.findByOwnerId(ownerId));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable String id, @RequestBody Appointment request) {
        User owner = getLoggedInUser();

        Appointment existing = appointmentRepository.findById(new ObjectId(id))
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        if (!existing.getOwnerId().equals(owner.getId().toString())) {
            return ResponseEntity.status(403).body("You do not have permission to update this appointment");
        }

        existing.setStatus(request.getStatus());
        return ResponseEntity.ok(appointmentRepository.save(existing));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelAppointment(@PathVariable String id) {
        User user = getLoggedInUser();

        Appointment existing = appointmentRepository.findById(new ObjectId(id))
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        boolean isClient = existing.getClientId().equals(user.getId().toString());
        boolean isOwner = existing.getOwnerId().equals(user.getId().toString());

        if (!isClient && !isOwner) {
            return ResponseEntity.status(403).body("You do not have permission to cancel this appointment");
        }

        appointmentRepository.deleteById(new ObjectId(id));
        return ResponseEntity.ok("Appointment cancelled successfully");
    }
}