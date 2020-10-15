package com.zyv1.databasemanager.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zyv1.databasemanager.dao.DbinfoDao;
import com.zyv1.databasemanager.entity.Dbinfo;
import com.zyv1.databasemanager.util.ReturnMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApiService {
    @Autowired
    private DbinfoDao dbinfoDao;

    public ReturnMessage<Dbinfo> selectByDbname(String dbname){
        ReturnMessage<Dbinfo> returnMessage = new ReturnMessage<>();
        Dbinfo dbinfo = dbinfoDao.selectOne(new QueryWrapper<Dbinfo>().eq("dbname", dbname));
        if(dbinfo == null){
            returnMessage.failed("未找到相关数据库信息");
        }else{
            returnMessage.success(dbinfo);
        }
        return returnMessage;
    }
}
