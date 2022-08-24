package com.recarga.digitalsign.controller;

import com.recarga.digitalsign.model.Contract;
import com.recarga.digitalsign.service.ConvertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class ContractController{

    @Autowired
    ConvertService convertService;

    @PostMapping(path = "/pdf",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String convert(@RequestBody Contract contract){
        try {
            convertService.convertHtmlToPdf(contract);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "ok";
    }
}
