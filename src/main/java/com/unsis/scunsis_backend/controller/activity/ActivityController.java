package com.unsis.scunsis_backend.controller.activity;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/scunsis/api/v1/activity")
public class ActivityController {

    @GetMapping("/hello")    
    public String hello(){
        return "Hola";
    }
}
