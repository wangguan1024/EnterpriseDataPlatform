package com.zyv1.modelinvoking.service;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zyv1.modelinvoking.entity.ModelInfo;
import com.zyv1.modelinvoking.entity.ModelRelData;
import com.zyv1.modelinvoking.feign.FeignDatabaseConn;
import com.zyv1.modelinvoking.feign.FeignModelManager;
import com.zyv1.modelinvoking.util.ReturnMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;



@Service
public class ModelInvokingService {


    @Autowired
    private FeignModelManager feignModelManager;

    @Autowired
    private FeignDatabaseConn feignDatabaseConn;

    public static String postForJson(String url, JSONArray json){
        RestTemplate restTemplate = new RestTemplate();
        //设置Http Header
        HttpHeaders headers = new HttpHeaders();
        //设置请求媒体数据类型
        headers.setContentType(MediaType.APPLICATION_JSON);
        //设置返回媒体数据类型
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<String> formEntity = new HttpEntity<String>(json.toString(), headers);
        return restTemplate.postForObject(url, formEntity, String.class);
    }

    public ReturnMessage<Double> useModel(ModelRelData modelRelData) {
        ReturnMessage<Double> returnMessage = new ReturnMessage<>();

        //由已有数据库信息获取数据
        ReturnMessage<JSONArray> feignMessage = feignDatabaseConn.getDataForPredict(modelRelData);

        if ("failed".equals(feignMessage.getStatus())) {
            returnMessage.failed(feignMessage.getReason());
            return returnMessage;
        }
        JSONArray jsonArray = feignMessage.getData();//数据准备完成

        //由modelName获取modelInfo详细信息
        String modelName = modelRelData.getModelName();
        ReturnMessage<ModelInfo> feignMessageModel = feignModelManager.selectModelInfoByModelName(modelName);
        if ("failed".equals(feignMessageModel.getReason())) {
            returnMessage.failed(feignMessageModel.getReason());
            return returnMessage;
        }
        ModelInfo modelInfo = feignMessageModel.getData();

        //restful方式 算法调用
        String predictUrl = modelInfo.getPredictUrl();
        String predictResultStr = postForJson(predictUrl,jsonArray);
        try{
            Double predictResult = Double.parseDouble(predictResultStr);
            returnMessage.success(predictResult);
        }catch (Exception e){
            returnMessage.failed("模型调用返回数据格式不为double, 请检查模型 NOTE: "+e);
        }
        return returnMessage;
    }
}
