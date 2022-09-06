package com.example._tproject.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Log4j2
public class TestController {

    @GetMapping("/temp")
    public String temp(){
        return "myTemplate/template";
    }
}
