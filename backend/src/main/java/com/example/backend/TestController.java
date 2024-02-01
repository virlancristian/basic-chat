package com.example.backend;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @PostMapping(value = "/api/test", consumes = {MediaType.IMAGE_GIF_VALUE})
    public void  test() {}
}
