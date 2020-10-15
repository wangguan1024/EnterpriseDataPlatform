package com.zyv1.databaseconn.api;


import com.alibaba.fastjson.JSONArray;
import com.zyv1.databaseconn.util.ReturnMessage;
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

    @PostMapping("/predict")
    public ReturnMessage<JSONArray> getDataForPredict(@RequestBody DbinfoWithFieldNames dbinfoWithFieldNames){
        return apiService.getDataForPredict(dbinfoWithFieldNames);
    }

    @PostMapping("/itemcount")
    public ReturnMessage<Integer> getItemCount(@RequestBody DbinfoWithFieldNames dbinfoWithFieldNames){
        return apiService.getItemCount(dbinfoWithFieldNames);
    }
}
