package com.mysite.springbootboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
public class MainController {

    @CrossOrigin(originPatterns = "http://localhost:3000")
    @GetMapping("/")
    public String root() {
        return "redirect:/post/list";
    }

    @GetMapping("/board")
    @ResponseBody
    public String index() {
        return "게시판 예시";
    }

}
