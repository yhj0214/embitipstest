package com.example.embitipstest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public String test(){
        System.out.println("test");
        return "test";
    }
    @GetMapping("/test1")
    public String test1(){
        return "testtest";
    }
}
