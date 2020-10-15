package com.zyv1.mailserver.api;

import com.zyv1.mailserver.dao.WarnMailDao;
import com.zyv1.mailserver.entity.WarnMail;
import com.zyv1.mailserver.util.ReturnMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApiService {
    @Autowired
    private WarnMailDao warnMailDao;
    public ReturnMessage<String> insertMail(String message){
        ReturnMessage<String> returnMessage = new ReturnMessage<>();
        WarnMail warnMail = new WarnMail();
        warnMail.setMessage(message);
        if(warnMailDao.insert(warnMail)>0){
            returnMessage.success("");
        }else{
            returnMessage.failed("预警消息插入数据库失败");
        }
        return returnMessage;
    }
}
