package com.zyv1.modelinvoking.feign;

import com.zyv1.modelinvoking.entity.ModelInfo;
import com.zyv1.modelinvoking.util.ReturnMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("MODEL-MANAGER")
public interface FeignModelManager {
    @GetMapping("/api/modelname/{modelname}")
    public ReturnMessage<ModelInfo> selectModelInfoByModelName(String modelName);
}
