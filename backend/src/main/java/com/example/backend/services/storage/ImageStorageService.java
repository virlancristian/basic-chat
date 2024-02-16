package com.example.backend.services.storage;

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

    public boolean saveImage(BufferedImage image, String imageName, String imageFormat) {
        File imageLocation = new File(STORAGE_DIRECTORY + "/" + imageName + "." + imageFormat);

        try {
            ImageIO.write(image, imageFormat, imageLocation);
        } catch(IOException error) {
            System.out.println("Error in ImageStorageService::saveImage - failed to save image " + imageName + "." + imageFormat + ":\n" + error);
            return false;
        }

        return true;
    }

    public BufferedImage getImage(String imageName) {
        BufferedImage image = null;

        try {
            image = ImageIO.read(new File(STORAGE_DIRECTORY + "/" + imageName));
        } catch(IOException error) {
            System.out.println("Error in ImageStorageService::getImage - Unable to read image " + imageName +":\n" + error);
        }

        return image;
    }

    public boolean deleteImage(String imageName) {
        File image = new File(STORAGE_DIRECTORY + "/" + imageName);

        try {
            image.delete();
        } catch(SecurityException error) {
            System.out.println("Failed to delete image " + imageName + ":\n" + error);
            return false;
        }

        return true;
    }
}
