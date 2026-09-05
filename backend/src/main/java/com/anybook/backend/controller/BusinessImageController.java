package com.anybook.backend.controller;

import com.anybook.backend.entity.User;
import com.anybook.backend.repository.UserRepository;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class BusinessImageController {

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/{userId}/images")
    public ResponseEntity<?> uploadImage(
            @PathVariable String userId,
            @RequestParam("image") MultipartFile image
    ) throws IOException {

        User user = userRepository.findById(new ObjectId(userId))
                .orElseThrow(() -> new RuntimeException("User not found"));

        ObjectId imageId = gridFsTemplate.store(
                image.getInputStream(),
                image.getOriginalFilename(),
                image.getContentType()
        );

        user.getBusinessImageIds().add(imageId.toString());
        user.setBusinessImageCount(user.getBusinessImageIds().size());
        userRepository.save(user);

        return ResponseEntity.ok(imageId.toString());
    }

    @GetMapping("/{userId}/images")
    public ResponseEntity<List<String>> getImageIds(
            @PathVariable String userId
    ) {

        User user = userRepository.findById(new ObjectId(userId))
                .orElseThrow(() -> new RuntimeException("User not found"));

        return ResponseEntity.ok(user.getBusinessImageIds());
    }

    @GetMapping("/{userId}/images/{imageId}")
    public ResponseEntity<Resource> getImage(
            @PathVariable String userId,
            @PathVariable String imageId
    ) {

        User user = userRepository.findById(new ObjectId(userId))
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getBusinessImageIds().contains(imageId)) {
            return ResponseEntity.notFound().build();
        }

        GridFSFile file = gridFsTemplate.findOne(
                Query.query(
                        Criteria.where("_id").is(new ObjectId(imageId))
                )
        );

        if (file == null) {
            return ResponseEntity.notFound().build();
        }

        Resource resource = gridFsTemplate.getResource(file);

        MediaType mediaType = MediaTypeFactory
                .getMediaType(file.getFilename())
                .orElse(MediaType.APPLICATION_OCTET_STREAM);

        return ResponseEntity.ok()
                .contentType(mediaType)
                .body(resource);
    }
}