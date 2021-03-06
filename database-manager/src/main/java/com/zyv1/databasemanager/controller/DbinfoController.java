package com.zyv1.databasemanager.controller;


import com.zyv1.databasemanager.entity.Dbinfo;
import com.zyv1.databasemanager.service.DbinfoService;
import com.zyv1.databasemanager.util.ReturnMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/databasemanager/")
public class DbinfoController {
    @Autowired
    private DbinfoService dbinfoService;

    @GetMapping("/")
    public ReturnMessage<List<Dbinfo>> getAll(){
        return dbinfoService.selectAll();
    }

    @PostMapping("/")
    public ReturnMessage<String> Insert(@RequestBody Dbinfo dbinfo){
        return dbinfoService.Insert(dbinfo);
    }

    @DeleteMapping("/dbname/{dbname}")
    public ReturnMessage<String> DeleteByDbname(@PathVariable("dbname") String dbname) {
        return dbinfoService.DeleteByDbname(dbname);
    }

    @PutMapping("/")
    public ReturnMessage<String> Update(@RequestBody Dbinfo dbinfo) {
        return dbinfoService.Update(dbinfo);
    }
}

