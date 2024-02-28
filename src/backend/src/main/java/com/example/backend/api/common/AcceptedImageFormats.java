package com.example.backend.api.common;

import java.util.ArrayList;
import java.util.List;

public class AcceptedImageFormats {
    private static final String[] imageFormatArray = {"jpeg", "jpg", "png"};
    public static final List<String> IMAGE_FORMATS = new ArrayList<>(List.of(imageFormatArray));
}
