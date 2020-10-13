package com.zyv1.databasemanager.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zyv1.databasemanager.dao.DbinfoDao;
import com.zyv1.databasemanager.entity.Dbinfo;
import com.zyv1.databasemanager.util.ReturnMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DbinfoService {
    @Autowired
    private DbinfoDao dbinfoDao;


    public ReturnMessage<List<Dbinfo>> selectAll(){
        ReturnMessage<List<Dbinfo>> returnMessage = new ReturnMessage<>();
        List<Dbinfo> dbinfoList = dbinfoDao.selectList(null);
        if(dbinfoList.isEmpty()){
            returnMessage.failed("暂无数据库信息，请添加至少一个");
        }else{
            returnMessage.success(dbinfoList);
        }
        return returnMessage;
    }

    public ReturnMessage<String> Insert(Dbinfo dbinfo){
        ReturnMessage<String> returnMessage = new ReturnMessage<>();

        if(judgeRepeat("url", dbinfo.getUrl())){
            returnMessage.failed ("数据库Url已存在");
            return returnMessage;
        }
        if(judgeRepeat("dbname",dbinfo.getDbname())){
            returnMessage.failed  ("数据库名称重复");
            return returnMessage;
        }

        if (dbinfoDao.insert(dbinfo)>0){
            returnMessage.success("");
        }else{
            returnMessage.failed("数据库插入操作出现异常");
        }
        return returnMessage;
    }

    public ReturnMessage<String> DeleteByDbname(String dbname) {
        ReturnMessage<String> returnMessage = new ReturnMessage<>();

        if(dbinfoDao.deleteByMap(Map.of("dbname", dbname))==1){
            returnMessage.success("");
        }else{
            returnMessage.failed ("该dbname不存在");
        }
        return returnMessage;
    }


    //update操作不允许修改url
    public ReturnMessage<String> Update(Dbinfo dbinfo){
        ReturnMessage<String> returnMessage = new ReturnMessage<>();

        if(judgeRepeat("dbname", dbinfo.getDbname())){
            returnMessage.failed  ("数据库名称重复");
            return returnMessage;
        }

        if(dbinfoDao.update(dbinfo, new QueryWrapper<Dbinfo>().eq("url", dbinfo.getUrl()))>0){
            returnMessage.success("");
        }else{
            returnMessage.failed ("修改数据库信息失败");
        }
        return returnMessage;
    }

    //根据指定字段判断是否已经存在同名数据
    private boolean judgeRepeat(String fieldName, String fieldValue){
        return dbinfoDao.selectCount(new QueryWrapper<Dbinfo>().eq(fieldName, fieldValue)) > 0;
    }
}
