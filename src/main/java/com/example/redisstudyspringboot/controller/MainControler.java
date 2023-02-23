package com.example.redisstudyspringboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainControler {

    @RequestMapping("/index")
    @ResponseBody
    public String aa(){
        return "hello world";
    }

}
