package com.zyv1.warnserver.service;

import com.zyv1.warnserver.dao.WarnInfoDao;
import com.zyv1.warnserver.entity.WarnInfo;
import com.zyv1.warnserver.feign.FeignDatabaseConn;
import com.zyv1.warnserver.feign.FeignModelInvoking;
import com.zyv1.warnserver.feign.FeignWarnMail;
import com.zyv1.warnserver.util.ReturnMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WarnServerService {
    @Autowired
    private WarnInfoDao warnInfoDao;

    @Autowired
    private FeignDatabaseConn feignDatabaseConn;

    @Autowired
    private FeignModelInvoking feignModelInvoking;

    @Autowired
    private FeignWarnMail feignWarnMail;

    public ReturnMessage<String> startWarnServer(Integer id){

        ReturnMessage<String> returnMessage = new ReturnMessage<>();

        WarnInfo warnInfo = warnInfoDao.selectById(id);

        //获取最新数据
        ReturnMessage<Integer> feignDatabaseConnReturnMessage = feignDatabaseConn.getItemCount(warnInfo);
        if("failed".equals(feignDatabaseConnReturnMessage.getStatus())){
            returnMessage.failed(feignDatabaseConnReturnMessage.getReason());
            return returnMessage;
        }
        Integer currentItemCount = feignDatabaseConnReturnMessage.getData();


        //首先查看监控对象的数据数量是否更新
        //若未更新,不调用算法
        if(currentItemCount.equals(warnInfo.getItemNum())){
            returnMessage.success("");
            return returnMessage;
        }else{//调用算法获取最新的预测值
            warnInfo.setItemNum(currentItemCount);
            ReturnMessage<Double> predictReturnMessage = feignModelInvoking.useModel(warnInfo);

            if("failed".equals(predictReturnMessage.getStatus())){
                returnMessage.failed(predictReturnMessage.getReason());
                return returnMessage;
            }

            Double predictResult = predictReturnMessage.getData();
            if(predictResult<0.) {predictResult = 0.;}
            Double warnLine = warnInfo.getWarnLine();
            Boolean isBigger = warnInfo.getIsBigger();

            ReturnMessage<String> mailReturnMessage = new ReturnMessage<>();
            //预测数据与结果比对
            if(isBigger){
                String condition = "高于";
                if(predictResult>warnLine){
                    StringBuilder sb = new StringBuilder();
                    sb.append("模型名称：").append(warnInfo.getDbname())
                            .append(";")
                            .append("监控数据：").append(warnInfo.getDbname()).append(",")
                            .append(warnInfo.getTableName()).append(",").append(warnInfo.getTaskField())
                            .append(";")
                            .append("预测值：").append(predictResult.toString())
                            .append(";")
                            .append(condition)
                            .append("临界值：").append(warnLine.toString());
                    String message = sb.toString();
                    mailReturnMessage = feignWarnMail.insertMail(message);

                }
            }else{
                String condition = "低于";
                if(predictResult<warnLine){
                    StringBuilder sb = new StringBuilder();
                    sb.append("模型名称：").append(warnInfo.getDbname())
                            .append(";")
                            .append("监控数据：").append(warnInfo.getDbname()).append(",")
                            .append(warnInfo.getTableName()).append(",").append(warnInfo.getTaskField())
                            .append(";")
                            .append("预测值：").append(predictResult.toString())
                            .append(";")
                            .append(condition)
                            .append("临界值：").append(warnLine.toString());
                    String message = sb.toString();
                    mailReturnMessage = feignWarnMail.insertMail(message);
                }
            }
            if(mailReturnMessage.getStatus().equals("failed")){
                returnMessage.failed(mailReturnMessage.getReason());
            }else{
                returnMessage.success("");
            }
            return returnMessage;
        }

    }

}
