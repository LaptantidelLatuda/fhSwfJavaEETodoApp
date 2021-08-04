package com.webapp.backend.controllers;

import java.util.HashMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
public class HomeController {
    @GetMapping("/hello")
    public HashMap<String, String> home()
    {
        String answer = "Hello from mySpringBoot";
        String date = "02.08.21";
        HashMap<String, String> map = new HashMap<>();
        map.put(answer, date);
        
        return map;
    }
}
