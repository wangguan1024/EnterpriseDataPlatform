package com.zyv1.mailserver.controller;


import com.zyv1.mailserver.entity.WarnMail;
import com.zyv1.mailserver.service.WarnMailService;
import com.zyv1.mailserver.util.ReturnMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mailserver")
public class WarnMailController {

    @Autowired
    private WarnMailService warnMailService;
    @GetMapping("/")
    public ReturnMessage<List<WarnMail>> selectAll(){
        return warnMailService.selectAll();
    }

    @GetMapping("/deleted")
    public ReturnMessage<List<WarnMail>> selectDeleted(){
        return warnMailService.selectDeleted();
    }

    @PutMapping("/")
    public ReturnMessage<String> updateState(@RequestBody WarnMail warnMail){
        return warnMailService.updateState(warnMail);
    }
}
