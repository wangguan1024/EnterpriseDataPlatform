package com.zyv1.databasemanager.controller;


import com.zyv1.databasemanager.entity.Dbinfo;
import com.zyv1.databasemanager.service.ApiService;
import com.zyv1.databasemanager.util.ReturnMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {
    @Autowired
    private ApiService apiService;

    @GetMapping("/dbname/{dbname}")
    public ReturnMessage<Dbinfo> selectByDbname(@PathVariable("dbname") String dbname){
        //测试熔断机制
//        try{
//            Thread.sleep(30000);
//
//        }catch (Exception e){
//            System.out.println(e);
//        }
//        throw new RuntimeException("");
        return apiService.selectByDbname(dbname);
    }
}
