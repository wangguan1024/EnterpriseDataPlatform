package com.zyv1.databaseconn.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.zyv1.databaseconn.bean.Dbinfo;
import com.zyv1.databaseconn.service.SelectService;

import com.zyv1.databaseconn.util.ReturnMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/databaseconn")
public class SelectController {
    @Autowired
    private SelectService selectService;

    @GetMapping("/dbname/{dbname}/tablename/{tablename}")
    public ReturnMessage<JSONArray> selectAllByTableName(

            @PathVariable("dbname") String dbname,
            @PathVariable("tablename") String tableName) {
        return selectService.selectAllByTableName(dbname, tableName);
    }
}
