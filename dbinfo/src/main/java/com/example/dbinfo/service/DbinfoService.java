package com.example.dbinfo.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.example.dbinfo.dao.DbinfoDao;
import com.example.dbinfo.entity.Dbinfo;
import com.example.dbinfo.util.ReturnFailed;
import com.example.dbinfo.util.ReturnMessage;
import com.example.dbinfo.util.ReturnSuccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class DbinfoService {
    @Autowired
    private DbinfoDao dbinfoDao;


    public List<Dbinfo> selectAll(){
        return dbinfoDao.selectList(null);
    }

    public Dbinfo selectByUrl(String url){
        return dbinfoDao.selectOne(new QueryWrapper<Dbinfo>().eq("url", url));
    }

    public ReturnMessage Insert(Dbinfo dbinfo){
        if(judgeRepeat("url", dbinfo.getUrl())){
            return new ReturnFailed("URL REPEAT");
        }
        if(judgeRepeat("dbname",dbinfo.getDbname())){
            return new ReturnFailed("DBNAME REPEAT");
        }
        if(dbinfoDao.insert(dbinfo)>0){
            return new ReturnSuccess();
        }else{
            return new ReturnFailed("DAO ERROR");
        }
    }

    public ReturnMessage DeleteByUrl(String url) {
        if(!judgeRepeat("url", url)){
            return new ReturnFailed("URL NOT FOUND");
        }
        if(dbinfoDao.deleteByMap(Map.of("url", url))==1){
            return new ReturnSuccess();
        }else{
            return new ReturnFailed("DAO ERROR");
        }
    }

    public ReturnMessage Update(Dbinfo dbinfo){
        if(dbinfoDao.updateById(dbinfo)>0){
            return new ReturnSuccess();
        }else{
            return new ReturnFailed("DAO ERROR");
        }
    }

    //根据指定字段判断是否已经存在同名数据
    private boolean judgeRepeat(String fieldName, String fieldValue){
        return dbinfoDao.selectCount(new QueryWrapper<Dbinfo>().eq(fieldName, fieldValue)) > 0;
    }
}
