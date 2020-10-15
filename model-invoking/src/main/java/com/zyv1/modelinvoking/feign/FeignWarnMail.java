package com.zyv1.modelinvoking.feign;


import com.zyv1.modelinvoking.util.ReturnMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("MAIL-SERVER")
public interface FeignWarnMail {
    @PostMapping("/api/mail")
    public ReturnMessage<String> insertMail(@RequestBody String message);
}
