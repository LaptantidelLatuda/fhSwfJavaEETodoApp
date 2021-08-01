package com.webapp.backend.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
public class HomeController {
    @GetMapping("/hello")
    public String home()
    {
        String answer = "Hello from mySpringBoot";
        return answer;
    }
}
