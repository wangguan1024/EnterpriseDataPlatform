package com.zyv1.mailserver.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zyv1.mailserver.dao.WarnMailDao;
import com.zyv1.mailserver.entity.WarnMail;
import com.zyv1.mailserver.util.ReturnMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarnMailService {
    @Autowired
    private WarnMailDao warnMailDao;

    public ReturnMessage<List<WarnMail>> selectAll(){
        ReturnMessage<List<WarnMail>> returnMessage = new ReturnMessage<>();
        List<WarnMail> warnMailList = warnMailDao.selectList(new QueryWrapper<WarnMail>().eq("is_deleted", 0));
        returnMessage.success(warnMailList);
        return returnMessage;
    }

    public ReturnMessage<List<WarnMail>> selectDeleted(){
        ReturnMessage<List<WarnMail>> returnMessage = new ReturnMessage<>();
        List<WarnMail> warnMailList  = warnMailDao.selectList(new QueryWrapper<WarnMail>().eq("is_deleted", 1));
        returnMessage.success(warnMailList);
        return returnMessage;
    }

    public ReturnMessage<String> updateState(WarnMail warnMail){
        ReturnMessage<String> returnMessage = new ReturnMessage<>();
        if(warnMailDao.updateById(warnMail)==0){
            returnMessage.failed("warnMail更新失败");
        }else{
            returnMessage.success("");
        }
        return returnMessage;
    }

}
