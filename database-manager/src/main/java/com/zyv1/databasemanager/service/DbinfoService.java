package com.zyv1.databasemanager.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zyv1.databasemanager.dao.DbinfoDao;
import com.zyv1.databasemanager.entity.Dbinfo;
import com.zyv1.databasemanager.util.ReturnMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
public class DbinfoService {
    @Autowired
    private DbinfoDao dbinfoDao;



    public ReturnMessage<List<Dbinfo>>selectAll(){
        ReturnMessage<List<Dbinfo>> returnMessage = new ReturnMessage<>();
        List<Dbinfo> dbinfoList = dbinfoDao.selectList(null);
        returnMessage.success(dbinfoList);
        return returnMessage;
    }

    public ReturnMessage<String> Insert(Dbinfo dbinfo){
        ReturnMessage<String> returnMessage = new ReturnMessage<>();

        if(judgeRepeat("dbname",dbinfo.getDbname())){
            returnMessage.failed  ("数据库名称重复");
            return returnMessage;
        }

        String url = processUrl(dbinfo.getDbtype(), dbinfo.getHost(),dbinfo.getPort(),dbinfo.getConnName());

        dbinfo.setUrl(url);

        if(judgeRepeat("url", dbinfo.getUrl())){
            returnMessage.failed ("数据库已存在");
            return returnMessage;
        }

        try (Connection conn = DriverManager.getConnection(url, dbinfo.getUsername(), dbinfo.getPassword())){
            returnMessage.success("");
        }catch (SQLException e){
            returnMessage.failed("新数据库连接失败, 请检查输入数据是否正确");
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
            returnMessage.failed ("该数据库不存在");
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

    private String processUrl(String dbtype, String host, String port, String connName){
        if(dbtype.equals("mysql")){
            return "jdbc:mysql://"+host+":"+port+"/"+connName;
        }
        if(dbtype.equals("oracle")){
            return "jdbc:oracle:thin:@"+host+":"+port+":"+connName;
        }
        return "";
    }
}
