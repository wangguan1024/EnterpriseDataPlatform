package com.example.dbinfo.service;


import com.example.dbinfo.dao.DbinfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DbinfoService {
    @Autowired
    private DbinfoDao dbinfoDao;
    public String getAll(){
        return dbinfoDao.selectList()
    }
}
