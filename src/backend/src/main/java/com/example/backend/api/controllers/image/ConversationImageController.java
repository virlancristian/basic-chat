package com.example.backend.api.controllers.image;

import com.example.backend.api.common.AcceptedImageFormats;
import com.example.backend.api.common.RequestCode;
import com.example.backend.models.api.response.ControllerBasicResponse;
import com.example.backend.models.api.response.ImageUploadResponse;
import com.example.backend.models.database.ConversationDbEntity;
import com.example.backend.services.database.SentImageDbService;
import com.example.backend.services.storage.ImageStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/api/conversation")
public class ConversationImageController {
    private ImageStorageService imageStorageService;
    private SentImageDbService sentImageDbService;

    @Autowired
    public ConversationImageController(ImageStorageService imageStorageService,
                                       SentImageDbService sentImageDbService) {
        this.imageStorageService = imageStorageService;
        this.sentImageDbService = sentImageDbService;
    }

    @CrossOrigin
    @PostMapping("/{id}/image/upload")
    public ResponseEntity<ImageUploadResponse> uploadImage(@PathVariable Long id,
                                                           MultipartFile image) {
        RequestCode validationMessage = verifyUploadRequest(id, image);
        String fullImagename = null;
        Long imageId = null;

        if(validationMessage != RequestCode.OK) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ImageUploadResponse(validationMessage, null, null, null));
        }

        fullImagename = image.getOriginalFilename();
        imageId = sentImageDbService.getConversationImageNumber(id) + 1;

        imageStorageService.saveImage(convertToBufferedImage(image),
                                "conversation_" + id.toString(),
                                        imageId + "_" + getImageNameInfo(fullImagename, false),
                                        getImageNameInfo(fullImagename, true));

        return ResponseEntity.status(HttpStatus.OK).body(new ImageUploadResponse(RequestCode.OK, image.getOriginalFilename(), id, imageId));
    }

    @CrossOrigin
    @GetMapping(value = "/{id}/image/{imageName}", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public ResponseEntity<Resource> getImage(@PathVariable Long id,
                                             @PathVariable String imageName,
                                             @RequestParam("image_id") Long imageId) {
        BufferedImage image = imageStorageService.getImage("conversation_" + id.toString(), imageId.toString() + "_" + imageName);
        ByteArrayResource imageData = convertToResource(image, getImageNameInfo(imageName, true));
        long imageContentLength = imageData != null ? imageData.contentLength() : 0;

        return ResponseEntity.status(HttpStatus.OK).contentLength(imageContentLength).body(imageData);
    }

    @CrossOrigin
    @DeleteMapping("/{id}/image/{imageName}/delete")
    public ResponseEntity<ControllerBasicResponse> deleteImage(@PathVariable Long id,
                                                               @PathVariable String imageName,
                                                               @RequestParam("image_id") Long imageId) {
        boolean operationSuccessful = imageStorageService.deleteImage("conversation_" + id.toString(), imageId.toString() + "_" + imageName);

        return operationSuccessful ?
                ResponseEntity.status(HttpStatus.OK).body(new ControllerBasicResponse("SUCCESS", RequestCode.OK)) :
                ResponseEntity.status(HttpStatus.OK).body(new ControllerBasicResponse("ERROR", RequestCode.CANNOT_DELETE_IMAGE));
    }

    private RequestCode verifyUploadRequest(Long id, MultipartFile image) {
        if(!sentImageDbService.conversationExists(id)) {
            return RequestCode.CONVERSATION_NOT_FOUND;
        }

        if(image == null) {
            return RequestCode.NULL_IMAGE;
        }

        if(!AcceptedImageFormats
                                .IMAGE_FORMATS
                                .contains(getImageNameInfo(image.getOriginalFilename(), true))) {
            return RequestCode.INVALID_IMAGE_FORMAT;
        }

        return RequestCode.OK;
    }

    private String getImageNameInfo(String fullImageName, boolean extensionRequired) {
        String[] split = fullImageName.split("\\.");

        return extensionRequired ? split[1] : split[0];
    }

    private byte[] getImageBytes(MultipartFile image) {
        byte[] data = null;

        try {
            data = image.getBytes();
        } catch(IOException error) {
            System.out.println("Error in ImageController::getImageBytes - unable to get image bytes for image" +
                    image.getOriginalFilename() + ":\n" + error);
        }

        return data;
    }

    private BufferedImage convertToBufferedImage(MultipartFile image) {
        byte[] imageData = getImageBytes(image);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageData);
        BufferedImage convertedImage = null;

        try {
            if(image == null) {
                return null;
            } else {
                convertedImage = ImageIO.read(byteArrayInputStream);
            }
        } catch(IOException error) {
            System.out.println("Error in ImageController:convertToBufferedImage - failed to read from InputStream for image " + image.getOriginalFilename() + ":\n" + error);
        }

        return convertedImage;
    }

    private ByteArrayResource convertToResource(BufferedImage image, String imageFormat) {
        ByteArrayOutputStream imageAOS = new ByteArrayOutputStream();

        try {
            if(image == null) {
                return null;
            } else {
                ImageIO.write(image, imageFormat, imageAOS);
            }
        } catch (IOException error) {
            System.out.println("Error in GetImageController::convertToResource - failed to write image to byte array:\n" + error);
        }

        return new ByteArrayResource(imageAOS.toByteArray());
    }
}
