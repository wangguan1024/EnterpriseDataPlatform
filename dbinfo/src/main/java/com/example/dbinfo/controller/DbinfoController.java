package com.example.dbinfo.controller;


import com.example.dbinfo.entity.Dbinfo;
import com.example.dbinfo.service.DbinfoService;
import com.example.dbinfo.util.ReturnMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("dbinfo")
public class DbinfoController {
    @Autowired
    private DbinfoService dbinfoService;

    @GetMapping("/")
    public List<Dbinfo> getAll(){
        return dbinfoService.selectAll();
    }

    @GetMapping("/url/{url}")
    public Dbinfo selectByUrl(@PathVariable("url") String url){
        return dbinfoService.selectByUrl(url);
    }

    @PostMapping("/")
    public ReturnMessage Insert(@RequestBody Dbinfo dbinfo){
        return dbinfoService.Insert(dbinfo);
    }

    @DeleteMapping("/url/{url}")
    public ReturnMessage DeleteByUrl(@PathVariable("url") String url) {
        return dbinfoService.DeleteByUrl(url);
    }

    @PutMapping("/")
    public ReturnMessage Update(@RequestBody Dbinfo dbinfo) {
        return dbinfoService.Update(dbinfo);
    }
}

