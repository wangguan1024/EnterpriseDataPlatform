package com.example.dbinfo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("dbinfo")
public class DbinfoController {
    @Autowired
    private

    @GetMapping("/getAll")
    public String getAll(){

    }
}

