package com.zyv1.databaseconn.controller;


import com.alibaba.fastjson.JSONArray;
import com.zyv1.databaseconn.service.SelectService;

import com.zyv1.databaseconn.util.ReturnMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/databaseconn")
public class SelectController {
    @Autowired
    private SelectService selectService;

    @GetMapping("/tablenames/dbname/{dbname}")
    public ReturnMessage<String> getTableNames(@PathVariable("dbname") String dbname){
       return selectService.GetTableNames(dbname);
    }

    //要增加查询字段名

    @GetMapping("/data/dbname/{dbname}/tablename/{tablename}")
    public ReturnMessage<JSONArray> selectAllByTableName(

            @PathVariable("dbname") String dbname,
            @PathVariable("tablename") String tableName) {
        return selectService.selectAllByTableName(dbname, tableName);
    }

}
