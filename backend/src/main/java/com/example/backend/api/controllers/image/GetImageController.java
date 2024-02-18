package com.example.backend.api.controllers.image;

import com.example.backend.services.storage.ImageStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
public class GetImageController {
    private ImageStorageService imageStorageService;

    @Autowired
    public GetImageController(ImageStorageService imageStorageService) {
        this.imageStorageService = imageStorageService;
    }

    @GetMapping(value = "/api/image/get", produces = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
    public ResponseEntity<Resource> getImage(@RequestParam("name") String imageName) {
        BufferedImage image = imageStorageService.getImage(imageName);
        String[] splitImageName = imageName.split("\\.");
        ByteArrayResource imageData = convertToResource(image, splitImageName[1]);

        return ResponseEntity.status(HttpStatus.OK).contentLength(imageData.contentLength()).body(imageData);
    }

    private ByteArrayResource convertToResource(BufferedImage image, String imageFormat) {
        ByteArrayOutputStream imageAOS = new ByteArrayOutputStream();

        try {
            ImageIO.write(image, imageFormat, imageAOS);
        } catch (IOException error) {
            System.out.println("Error in GetImageController::convertToResource - failed to write image to byte array:\n" + error);
        }

        return new ByteArrayResource(imageAOS.toByteArray());
    }
}
