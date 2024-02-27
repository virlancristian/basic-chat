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

    public boolean saveImage(BufferedImage image, String directory, String imageName, String imageFormat) {
        File directoryLocation = new File(STORAGE_DIRECTORY + "/" + directory);
        File imageLocation = new File(STORAGE_DIRECTORY
                                        + "/"
                                        + ((directory != null && !directory.isEmpty()) ? directory + "/" : "")
                                        + imageName + "." + imageFormat);

        if(!directoryLocation.exists()) {
            directoryLocation.mkdir();
        }

        try {
            ImageIO.write(image, imageFormat, imageLocation);
        } catch(IOException error) {
            System.out.println("Error in ImageStorageService::saveImage - failed to save image " + imageName + "." + imageFormat + ":\n" + error);
            return false;
        }

        return true;
    }

    public BufferedImage getImage(String directory, String imageName) {
        BufferedImage image = null;

        try {
            image = ImageIO.read(new File(STORAGE_DIRECTORY
                                            + "/"
                                            + ((directory != null && !directory.isEmpty()) ? directory + "/" : "")
                                            + imageName));
        } catch(IOException error) {
            System.out.println("Error in ImageStorageService::getImage - Unable to read image " + imageName +":\n" + error);
        }

        return image;
    }

    public boolean deleteImage(String directory, String imageName) {
        File image = new File(STORAGE_DIRECTORY
                                + "/"
                                + ((directory != null && !directory.isEmpty()) ? directory + "/" : "")
                                + imageName);

        try {
            image.delete();
        } catch(SecurityException error) {
            System.out.println("Failed to delete image " + imageName + ":\n" + error);
            return false;
        }

        return true;
    }
}
