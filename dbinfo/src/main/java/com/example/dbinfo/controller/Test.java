package com.example.dbinfo.controller;


import com.alibaba.fastjson.JSON;
import com.example.dbinfo.dao.DbinfoDao;
import com.example.dbinfo.entity.Dbinfo;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;

@RestController
@RequestMapping("/test")
public class Test {

    @GetMapping("/get")
    public String getDbinfo() {
        Dbinfo dbinfo = new Dbinfo();
        dbinfo.setId(1);
        dbinfo.setUrl("http://");
        dbinfo.setUsername("rrr");
        dbinfo.setPassword("ppp");
        dbinfo.setDbname("testdb");
        dbinfo.setTableStr("666,777,888");
        return JSON.toJSONString(dbinfo);
    }
}
