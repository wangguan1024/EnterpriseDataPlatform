package com.zyv1.mailserver.api;

import com.zyv1.mailserver.util.ReturnMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {
    @Autowired
    private ApiService apiService;

    @PostMapping("/mail")
    public ReturnMessage<String> insertMail(@RequestBody String message){
        return apiService.insertMail(message);
    }
}
