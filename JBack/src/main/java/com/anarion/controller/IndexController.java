package com.anarion.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @RequestMapping("/hello")
    public String hello() {
        return "hello.html";
    }

    @RequestMapping("/")
    public String indexRoot() {
        return "index.html";
    }
}
