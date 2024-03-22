package com.example.backend.api.controllers.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageRouterController {
    @GetMapping(value = {"/", "/home", "/auth", "/create_account", "/login"})
    public String router() {
        return "../static/index";
    }
}
