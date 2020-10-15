package com.zyv1.warnserver.controller;


import com.zyv1.warnserver.entity.WarnInfo;
import com.zyv1.warnserver.service.WarnInfoService;
import com.zyv1.warnserver.util.ReturnMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/warndata")
public class WarnInfoController {
    @Autowired
    private WarnInfoService warnInfoService;

    @GetMapping("/")
    public ReturnMessage<List<WarnInfo>> selectAll(){
        return warnInfoService.selectAll();
    }

    @PostMapping("/")
    public ReturnMessage<String> insert(@RequestBody WarnInfo warnInfo){
        return warnInfoService.insert(warnInfo);
    }

    @PutMapping("/")
    public ReturnMessage<String> update(@RequestBody WarnInfo warnInfo){
        return warnInfoService.update(warnInfo);
    }

    @DeleteMapping("/id/{id}")
    public ReturnMessage<String> deleteById(@PathVariable("id") Integer id){
        return warnInfoService.deleteById(id);
    }
}
