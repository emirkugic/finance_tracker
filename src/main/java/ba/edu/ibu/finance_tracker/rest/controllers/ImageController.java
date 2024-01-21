package ba.edu.ibu.finance_tracker.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import ba.edu.ibu.finance_tracker.core.service.ImageService;

import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/image")
public class ImageController {

    @Autowired
    private ImageService imgurService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file,
            @RequestParam("userId") String userId) {
        try {
            String imgUrl = imgurService.uploadImage(file, userId);
            return ResponseEntity.ok(imgUrl);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to upload image: " + e.getMessage());
        }
    }

}
