package com.overwall.demo.practice;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Controller
public class HelloController {

    @RequestMapping("/success")
    public String Hello(Map<String, Object> map) {
        map.put("hello", "你好");
        return "success";
    }

    @RequestMapping("/hello1")
    public String hello() {
        return "Hello World";
    }
}
