package com.example.backend.api.controllers.image;

import com.example.backend.api.common.AcceptedImageFormats;
import com.example.backend.api.common.RequestCode;
import com.example.backend.models.api.requests.ImageUploadRequest;
import com.example.backend.services.database.ConversationDbSevice;
import com.example.backend.services.storage.ImageStorageService;
import com.example.backend.services.database.SentImageDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@RestController
public class UploadImageController {
    private ImageStorageService imageStorageService;
    private ConversationDbSevice conversationDbSevice;
    private SentImageDbService sentImageDbService;

    @Autowired
    public UploadImageController(ImageStorageService imageStorageService,
                                 ConversationDbSevice conversationDbSevice,
                                 SentImageDbService sentImageDbService) {
        this.imageStorageService = imageStorageService;
        this.conversationDbSevice = conversationDbSevice;
        this.sentImageDbService = sentImageDbService;
    }

    @CrossOrigin
    @PutMapping("/api/image/upload")
    public ResponseEntity<String> uploadImage(@ModelAttribute ImageUploadRequest conversationData) {
        String newImageName;
        String imageFormat;
        String[] splitImageName;
        BufferedImage image;
        boolean saveSuccessful;

        System.out.println(verifyRequest(conversationData));

        if(verifyRequest(conversationData) != RequestCode.OK) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        splitImageName = conversationData.getImage().getOriginalFilename().split("\\.");
        newImageName = createNewImageName(conversationData.getConversationId(), splitImageName[0]);
        imageFormat = splitImageName[1];

        image = convertToBufferedImage(conversationData.getImage());

        saveSuccessful = imageStorageService.saveImage(image, newImageName, imageFormat);

        return saveSuccessful ?
                ResponseEntity.status(HttpStatus.OK).body(newImageName  + "." + imageFormat) :
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    private RequestCode verifyRequest(ImageUploadRequest request) {
        MultipartFile image = request.getImage();
        String[] imageNameSplit = image.getOriginalFilename().split("\\.");

        if(conversationDbSevice.getConversationById(request.getConversationId()) == null) {
            return RequestCode.CONVERSATION_NOT_FOUND;
        }

        if(image == null) {
            return RequestCode.NULL_IMAGE;
        }
        System.out.println(imageNameSplit[1]);
        if(!AcceptedImageFormats.IMAGE_FORMATS.contains(imageNameSplit[1])) {
            return RequestCode.INVALID_IMAGE_FORMAT;
        }

        return RequestCode.OK;
    }

    private byte[] getImageBytes(MultipartFile image) {
        byte[] data = null;

        try {
            data = image.getBytes();
        } catch(IOException error) {
            System.out.println("Error in UploadImageController::getImageBytes - unable to get image bytes for image" +
                                image.getOriginalFilename() + ":\n" + error);
        }

        return data;
    }

    private BufferedImage convertToBufferedImage(MultipartFile image) {
        byte[] imageData = getImageBytes(image);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageData);
        BufferedImage convertedImage = null;

        try {
            convertedImage = ImageIO.read(byteArrayInputStream);
        } catch(IOException error) {
            System.out.println("Error in UploadImageController:converToBufferedImage - failed to read from InputStream for image " + image.getOriginalFilename() + ":\n" + error);
        }

        return convertedImage;
    }

    private String createNewImageName(Long conversationId, String imageName) {
        Long imageNumber = sentImageDbService.getConversationImageNumber(conversationId) + 1;
        StringBuilder newImageName = new StringBuilder("conversation_" + conversationId.toString() + "_" + imageNumber.toString() + "_" + imageName);

        return newImageName.toString();
    }
}
