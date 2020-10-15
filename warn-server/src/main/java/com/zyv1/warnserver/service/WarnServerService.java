package com.zyv1.warnserver.service;

import com.alibaba.fastjson.JSON;
import com.zyv1.warnserver.dao.WarnInfoDao;
import com.zyv1.warnserver.entity.WarnInfo;
import com.zyv1.warnserver.feign.FeignDatabaseConn;
import com.zyv1.warnserver.util.ReturnMessage;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WarnServerService {
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private WarnInfoDao warnInfoDao;

    @Autowired
    private FeignDatabaseConn feignDatabaseConn;


    public ReturnMessage<String> startWarnServer(Integer id){

        ReturnMessage<String> returnMessage = new ReturnMessage<>();

        WarnInfo warnInfo = warnInfoDao.selectById(id);
        //获取监测数据的最新数量
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
        }else{
            //更新currentItemCount
            warnInfo.setItemNum(currentItemCount);
            warnInfoDao.updateById(warnInfo);

            //传数据到WarnInfoQueue消息队列
            amqpTemplate.convertAndSend("WarnInfoQueue", JSON.toJSONString(warnInfo));
            returnMessage.success("");
            return returnMessage;
        }

    }

}
