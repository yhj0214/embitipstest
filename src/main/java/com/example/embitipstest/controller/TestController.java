package com.example.embitipstest.controller;

import com.example.embitipstest.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final TestService service;
    @GetMapping("/test/{name}")
    public String test(@PathVariable String name){
        System.out.println("receive name :" + name);
        String result = service.saveEntity(name).getName();
        return result;
    }
    @GetMapping("/test1")
    public String test1(){
        return "testtest";
    }
}
