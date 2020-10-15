package com.zyv1.modelinvoking.feign;


import com.alibaba.fastjson.JSONArray;

import com.zyv1.modelinvoking.entity.ModelRelData;
import com.zyv1.modelinvoking.util.ReturnMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("DATABASE-CONN")
public interface FeignDatabaseConn {
    @PostMapping("/api/predict")
    public ReturnMessage<JSONArray> getDataForPredict(@RequestBody ModelRelData modelRelData);
}
