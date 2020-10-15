package com.zyv1.warnserver.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zyv1.warnserver.dao.WarnInfoDao;
import com.zyv1.warnserver.entity.WarnInfo;
import com.zyv1.warnserver.util.ReturnMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarnInfoService {

    @Autowired
    private WarnInfoDao warnInfoDao;

    public ReturnMessage<List<WarnInfo>> selectAll(){
        ReturnMessage<List<WarnInfo>> returnMessage = new ReturnMessage<>();

        List<WarnInfo> list = warnInfoDao.selectList(null);
        if(list.isEmpty()){
            returnMessage.failed("预警系统无任务");
            return  returnMessage;
        }
        returnMessage.success(list);
        return returnMessage;
    }

    public ReturnMessage<String> insert(WarnInfo warnInfo){
        ReturnMessage<String> returnMessage = new ReturnMessage<>();
        if(warnInfoDao.insert(warnInfo)>0){
            returnMessage.success("");
        }else{
            returnMessage.failed("操作失败");
        }
        return returnMessage;
    }



    public ReturnMessage<String> update(WarnInfo warnInfo){
        ReturnMessage<String> returnMessage = new ReturnMessage<>();
        if(warnInfoDao.update(warnInfo,new QueryWrapper<WarnInfo>().eq("id", warnInfo.getId()))>0){
            returnMessage.success("");
        }else{
            returnMessage.failed("操作失败");
        }
        return returnMessage;
    }

    public ReturnMessage<String> deleteById(Integer id){
        ReturnMessage<String> returnMessage = new ReturnMessage<>();
        if(warnInfoDao.deleteById(id)==1){
            returnMessage.success("");
        }else{
            returnMessage.failed("操作失败");
        }
        return returnMessage;
    }
}
