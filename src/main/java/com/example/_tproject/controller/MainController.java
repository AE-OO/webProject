package com.example._tproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    //메인
    @RequestMapping("/")
    public String root() {
        return "home";
    }

    //소개
    @RequestMapping("/info")
    public String info() {
        return "info";
    }

}
