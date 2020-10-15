package com.zyv1.databaseconn.service;



import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zyv1.databaseconn.bean.Dbinfo;
import com.zyv1.databaseconn.feign.FeignDatabaseManager;
import com.zyv1.databaseconn.util.ReturnMessage;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class SelectService {

    @Autowired
    private FeignDatabaseManager feignDatabaseManager; //引入feign根据dbname获取数据库账号密码等信息

    public ReturnMessage<String> GetTableNames(String dbname)  {
        ReturnMessage<String> returnMessage = new ReturnMessage<>();


        ReturnMessage<Dbinfo> feignMessage = feignDatabaseManager.selectByDbname(dbname);
        if(feignMessage.getStatus().equals("failed")){
            returnMessage.failed(feignMessage.getReason());
            return returnMessage;
        }
        Dbinfo resultDbinfo = feignMessage.getData();

        try (Connection conn = DriverManager.getConnection(resultDbinfo.getUrl(), resultDbinfo.getUsername(), resultDbinfo.getPassword())) {
            try (PreparedStatement ps = conn.prepareStatement("SHOW TABLES")) {
                try(ResultSet rs = ps.executeQuery()){
                    List<String> tableNames = new ArrayList<>();
                    while(rs.next()){
                        String tableName = rs.getString(1);
                        tableNames.add(tableName);
                    }
                    if(tableNames.isEmpty()){
                        returnMessage.failed("数据库表名为空");
                    }
                    returnMessage.success(StringUtils.join(tableNames .toArray(), ","));
                }
            }
        }catch (SQLException e){
            returnMessage.failed(e.toString());
        }
        return returnMessage;
    }

    public ReturnMessage<JSONArray> selectAllByTableName(String dbname, String tableName){
        ReturnMessage<JSONArray> returnMessage = new ReturnMessage<>();

        //从databasemanager微服务获取数据库信息
        ReturnMessage<Dbinfo> feignMessage = feignDatabaseManager.selectByDbname(dbname);
        if(feignMessage.getStatus().equals("failed")){
            returnMessage.failed(feignMessage.getReason());
            return returnMessage;
        }
        Dbinfo resultDbinfo = feignMessage.getData();

        //查询数据
        try (Connection conn = DriverManager.getConnection(resultDbinfo.getUrl(), resultDbinfo.getUsername(), resultDbinfo.getPassword())) {
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tableName)) {
                try (ResultSet rs = ps.executeQuery()) {
                    JSONArray jsonArray = new JSONArray();
                    ResultSetMetaData resultSetMetaData = rs.getMetaData();
                    int columnNum = resultSetMetaData.getColumnCount();

                    while (rs.next()) {
                        com.alibaba.fastjson.JSONObject jsonObject = new JSONObject();
                        for (int i = 1; i <= columnNum; i++) {
                            String columnName = resultSetMetaData.getColumnName(i);
                            String value = rs.getString(columnName);
                            jsonObject.put(columnName,value);
                        }
                        jsonArray.add(jsonObject);
                    }

                    returnMessage.success(jsonArray);
                }
            }
        }catch (SQLException e){
            returnMessage.failed("数据库信息有误");
        }
        return returnMessage;
    }

}
