package com.zyv1.databaseconn.service;

import com.zyv1.databaseconn.bean.Dbinfo;
import com.zyv1.databaseconn.util.ReturnMessage;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Service
public class CheckService {
    public ReturnMessage<String> testconn(Dbinfo dbinfo){
        ReturnMessage<String> returnMessage = new ReturnMessage<>();
        try (Connection conn = DriverManager.getConnection(dbinfo.getUrl(), dbinfo.getUsername(), dbinfo.getPassword())){
            returnMessage.success("");
        }catch (SQLException e){
            returnMessage.failed("数据库信息有误, 请重试");
        }
        return returnMessage;
    }
}
