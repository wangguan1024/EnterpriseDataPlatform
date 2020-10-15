package com.zyv1.warnserver.service;

import com.zyv1.warnserver.dao.WarnInfoDao;
import com.zyv1.warnserver.entity.WarnInfo;
import com.zyv1.warnserver.feign.FeignDatabaseConn;
import com.zyv1.warnserver.feign.FeignModelInvoking;
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
            Double warnLine = warnInfo.getWarnLine();
            Boolean isBigger = warnInfo.getIsBigger();

            //预测数据与结果比对
            if(isBigger){
                if(predictResult>warnLine){
                    //消息队列发送报警数据
                }
            }else{
                if(predictResult<warnLine){
                    //消息队列发送报警数据
                }
            }
            returnMessage.success("");
            return returnMessage;
        }

    }
}
