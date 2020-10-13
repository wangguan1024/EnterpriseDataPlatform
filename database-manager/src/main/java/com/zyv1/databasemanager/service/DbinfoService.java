package com.zyv1.databasemanager.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zyv1.databasemanager.dao.DbinfoDao;
import com.zyv1.databasemanager.entity.Dbinfo;
import com.zyv1.databasemanager.feign.FeignDatabaseConn;
import com.zyv1.databasemanager.util.ReturnMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DbinfoService {
    @Autowired
    private DbinfoDao dbinfoDao;

    @Autowired
    private FeignDatabaseConn feignDatabaseConn;


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

        ReturnMessage<String> feignMessage = feignDatabaseConn.init(dbinfo);//通过feign调用database-conn初始化数据库tablenames信息

        if(feignMessage.getStatus().equals("success")){
            dbinfo.setTableStr(feignMessage.getData());
            if (dbinfoDao.insert(dbinfo)>0){
                returnMessage.success("");
            }else{
                returnMessage.failed("数据库插入操作出现异常");
            }
        }else{
            returnMessage.failed(feignMessage.getReason());
        }
        return returnMessage;
    }

    public ReturnMessage<String> DeleteByUrl(String url) {
        ReturnMessage<String> returnMessage = new ReturnMessage<>();
        if(!judgeRepeat("url", url)){
            returnMessage.failed("该Url不存在");
        }
        if(dbinfoDao.deleteByMap(Map.of("url", url))==1){
            returnMessage.success("");
        }else{
            returnMessage.failed ("数据库操作出现异常");
        }
        return returnMessage;
    }

    public ReturnMessage<String> Update(Dbinfo dbinfo){
        ReturnMessage<String> returnMessage = new ReturnMessage<>();
        if(dbinfoDao.updateById(dbinfo)>0){
            returnMessage.success("");
        }else{
            returnMessage.failed ("数据库操作出现异常");
        }
        return returnMessage;
    }

    //根据指定字段判断是否已经存在同名数据
    private boolean judgeRepeat(String fieldName, String fieldValue){
        return dbinfoDao.selectCount(new QueryWrapper<Dbinfo>().eq(fieldName, fieldValue)) > 0;
    }
}
