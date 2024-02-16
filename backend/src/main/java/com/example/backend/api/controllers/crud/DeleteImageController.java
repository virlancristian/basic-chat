package com.example.backend.api.controllers.crud;

import com.example.backend.api.common.RequestCode;
import com.example.backend.services.storage.ImageStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/image")
public class DeleteImageController {
    private ImageStorageService imageStorageService;

    @Autowired
    public DeleteImageController(ImageStorageService imageStorageService) {
        this.imageStorageService = imageStorageService;
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity deleteImage(@RequestParam("name") String fileName) {
        boolean deleteSuccessful;

        if(verifyRequest(fileName) != RequestCode.OK) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        deleteSuccessful = imageStorageService.deleteImage(fileName);

        return deleteSuccessful ? ResponseEntity.status(HttpStatus.OK).body(null) : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    private RequestCode verifyRequest(String filename) {
        return imageStorageService.getImage(filename) != null ? RequestCode.OK : RequestCode.IMAGE_NOT_FOUND;
    }
}
