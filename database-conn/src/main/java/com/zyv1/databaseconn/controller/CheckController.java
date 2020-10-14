package com.zyv1.databaseconn.controller;


import com.zyv1.databaseconn.bean.Dbinfo;
import com.zyv1.databaseconn.service.CheckService;
import com.zyv1.databaseconn.util.ReturnMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("databaseconn")
public class CheckController {
    @Autowired
    private CheckService checkService;

    @PostMapping("/testconn")
    public ReturnMessage<String> testconn(@RequestBody Dbinfo dbinfo){
        return checkService.testconn(dbinfo);
    }
}
