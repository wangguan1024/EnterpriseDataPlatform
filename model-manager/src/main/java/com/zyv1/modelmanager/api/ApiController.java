package com.zyv1.modelmanager.api;

import com.zyv1.modelmanager.entity.ModelInfo;
import com.zyv1.modelmanager.service.ModelManagerService;
import com.zyv1.modelmanager.util.ReturnMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {
    @Autowired
    private ApiService apiService;
    @GetMapping("/modelname/{modelname}")
    public ReturnMessage<ModelInfo> selectModelByModelName(@PathVariable("modelname") String modelName){
        return apiService.selectModelByModelName(modelName);
    }
}
