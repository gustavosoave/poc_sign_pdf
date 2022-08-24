package com.recarga.digitalsign.controller;

import com.recarga.digitalsign.service.SignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignController {


    @Autowired
    SignService signService;

    @GetMapping("/sign")
    public String sign(){
        signService.sign();
        return "ok";
    }
}
