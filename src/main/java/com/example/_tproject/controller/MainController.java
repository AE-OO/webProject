package com.example._tproject.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequestMapping("/yoopilates")
public class MainController {
    @GetMapping({"/home","/"})
    public String home(){
        log.info("Main Page...");
        return "/yoopilates/home";
    }

    @GetMapping("/info")
    public String info(){
        log.info("Info Page...");
        return "yoopilates/info";
    }
}
