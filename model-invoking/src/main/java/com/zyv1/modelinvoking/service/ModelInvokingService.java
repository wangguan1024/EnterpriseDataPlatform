package com.zyv1.modelinvoking.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.zyv1.modelinvoking.entity.ModelInfo;
import com.zyv1.modelinvoking.entity.ModelRelData;
import com.zyv1.modelinvoking.feign.FeignDatabaseConn;
import com.zyv1.modelinvoking.feign.FeignModelManager;
import com.zyv1.modelinvoking.feign.FeignWarnMail;
import com.zyv1.modelinvoking.util.ReturnMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Slf4j
@Component
@RabbitListener(queuesToDeclare = @Queue(value = "WarnInfoQueue",autoDelete = "true", durable = "true"))
public class ModelInvokingService {

    @Autowired
    private FeignModelManager feignModelManager;

    @Autowired
    private FeignDatabaseConn feignDatabaseConn;

    @Autowired
    private FeignWarnMail feignWarnMail;


    @RabbitHandler
    public void predict(String modelRelDataString){

        ModelRelData modelRelData = JSON.parseObject(modelRelDataString, ModelRelData.class);
        //由modelName获取modelInfo详细信息
        String modelName = modelRelData.getModelName();
        ReturnMessage<ModelInfo> feignMessageModel = feignModelManager.selectModelInfoByModelName(modelName);
        if ("failed".equals(feignMessageModel.getReason())) {
            log.warn("调用算法信息失败");
        }
        ModelInfo modelInfo = feignMessageModel.getData();
        String predictUrl = modelInfo.getPredictUrl();//算法准备完成

        //由数据库信息获取数据
        ReturnMessage<JSONArray> feignMessage = feignDatabaseConn.getDataForPredict(modelRelData);
        if ("failed".equals(feignMessage.getStatus())) {
            log.warn("调用数据信息失败");
        }
        JSONArray jsonArray = feignMessage.getData();//数据准备完成

        String predictResultStr = postForJson(predictUrl, jsonArray);
        Double predictResult = Double.parseDouble(predictResultStr);
        if(predictResult<0.) {predictResult = 0.;}

        Double warnLine = modelRelData.getWarnLine();
        Boolean isBigger = modelRelData.getIsBigger();
        log.info("算法预测值为"+predictResult);
        if(isBigger){
            String condition = "高于";
            if(predictResult>warnLine){
                StringBuilder sb = new StringBuilder();
                sb.append("模型名称：").append(modelRelData.getDbname())
                        .append(";")
                        .append("监控数据：").append(modelRelData.getDbname()).append(",")
                        .append(modelRelData.getTableName()).append(",").append(modelRelData.getTaskField())
                        .append(";")
                        .append("预测值：").append(predictResult.toString())
                        .append(";")
                        .append(condition)
                        .append("临界值：").append(warnLine.toString());
                String message = sb.toString();

                ReturnMessage<String> returnMessage = feignWarnMail.insertMail(message);
                log.info("预警信息插入信箱");

            }
        }else{
            String condition = "低于";
            if(predictResult<warnLine){
                StringBuilder sb = new StringBuilder();
                sb.append("模型名称：").append(modelRelData.getDbname())
                        .append(";")
                        .append("监控数据：").append(modelRelData.getDbname()).append(",")
                        .append(modelRelData.getTableName()).append(",").append(modelRelData.getTaskField())
                        .append(";")
                        .append("预测值：").append(predictResult.toString())
                        .append(";")
                        .append(condition)
                        .append("临界值：").append(warnLine.toString());
                String message = sb.toString();
                ReturnMessage<String> returnMessage = feignWarnMail.insertMail(message);

                log.info("预警信息插入信箱");
            }
        }
        log.info("插入信箱操作完成");

    }

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
}
