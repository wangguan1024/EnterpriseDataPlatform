package com.zyv1.modelinvoking.controller;

import com.alibaba.fastjson.JSONArray;
import com.zyv1.modelinvoking.entity.ModelRelData;
import com.zyv1.modelinvoking.feign.FeignModelManager;
import com.zyv1.modelinvoking.service.ModelInvokingService;
import com.zyv1.modelinvoking.util.ReturnMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/modelapi")
public class ModelInvokingController {
    @Autowired
    private ModelInvokingService modelInvokingService;

    @PostMapping("/")
    public ReturnMessage<Double> useModel(@RequestBody ModelRelData modelRelData){
        return modelInvokingService.useModel(modelRelData);
    }


}
