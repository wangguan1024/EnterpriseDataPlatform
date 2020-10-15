package com.zyv1.warnserver.controller;


import com.zyv1.warnserver.service.WarnServerService;
import com.zyv1.warnserver.util.ReturnMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/warnserver")
public class WarnServerController {

    @Autowired
    private WarnServerService warnServerService;

    @GetMapping("/id/{id}")
    public ReturnMessage<String> startWarnServer(@PathVariable("id") Integer id){
        return warnServerService.startWarnServer(id);
    }
}
