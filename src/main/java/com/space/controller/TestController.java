package com.space.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Controller
@RequestMapping("/pest")
@Validated
public class TestController {

    @GetMapping("/test1/{path}")
    @ResponseBody
    public String test1(@PathVariable @Min(1) @Max(4) String path){
        System.out.println(path);
        return path;
    }

    @GetMapping("/test2")
    @ResponseBody
    public String test2(@RequestParam("path") @Min(1) @Max(4) String path){
        System.out.println(path);
        return path;
    }

}
