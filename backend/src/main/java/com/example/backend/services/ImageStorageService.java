package com.example.backend.services;

import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Service
public class ImageStorageService {
    private static final String STORAGE_DIRECTORY = "images";

    public ImageStorageService() {
        File storageDirectory = new File(STORAGE_DIRECTORY);

        if(!storageDirectory.exists()) {
            storageDirectory.mkdir();
        }
    }

    public void saveImage(BufferedImage image, String imageName, String imageFormat) {
        File imageLocation = new File(STORAGE_DIRECTORY + "/" + imageName + "." + imageFormat);

        try {
            ImageIO.write(image, imageFormat, imageLocation);
        } catch(IOException error) {
            System.out.println("Error in ImageStorageService::saveImage - failed to save image:\n" + error);
        }
    }
}
