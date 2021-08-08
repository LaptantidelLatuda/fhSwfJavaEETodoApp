package com.webapp.backend.controllers;

import java.util.HashMap;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
public class HomeController {
    @PostMapping("/todo")
    public String saveString(@RequestBody String newToDO) {
        System.out.println("Insert new Etry: "+ newToDO);
        return newToDO;
    }

    @PutMapping("todo/{id}")
    public String updateString(@RequestBody String updateToDo, @PathVariable long id){
        System.out.println("Update an existing entry at id "+ id + " with new values " + updateToDo);
        return updateToDo;
    }

    @DeleteMapping("todo/{id}")
    public void get(@PathVariable Long id) {
        System.out.println("Delete an Entry at ID : " + id);
    }

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
