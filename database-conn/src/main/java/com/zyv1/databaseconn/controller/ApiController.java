package com.zyv1.databaseconn.controller;


import com.zyv1.databaseconn.bean.Dbinfo;
import com.zyv1.databaseconn.service.ApiService;

import com.zyv1.databaseconn.util.ReturnMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private ApiService apiService;

    @PostMapping("/init")
    //返回数据库tableNames，逗号分隔
    public ReturnMessage<String> init(@RequestBody Dbinfo dbinfo){
          return apiService.AutoGetTableNames(dbinfo.getUrl(), dbinfo.getUsername(), dbinfo.getPassword());
    }
}
