package com.zyv1.databaseconn.api;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zyv1.databaseconn.bean.Dbinfo;
import com.zyv1.databaseconn.feign.FeignDatabaseManager;
import com.zyv1.databaseconn.util.ReturnMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service
public class ApiService {
    @Autowired
    private FeignDatabaseManager feignDatabaseManager;//引入feign根据dbname获取数据库账号密码等信息

    public ReturnMessage<JSONArray> getDataForPredict(DbinfoWithFieldNames dbinfoWithFieldNames){
        ReturnMessage<JSONArray> returnMessage = new ReturnMessage<>();

        String dbname = dbinfoWithFieldNames.getDbname();
        String tableName = dbinfoWithFieldNames.getTableName();
        String orderField = dbinfoWithFieldNames.getOrderField();
        boolean isOrderReverse =dbinfoWithFieldNames.getIsOrderReverse();
        String taskField = dbinfoWithFieldNames.getTaskField();

        ReturnMessage<Dbinfo> feignMessage = feignDatabaseManager.selectByDbname(dbname);
        if(feignMessage.getStatus().equals("failed")){
            returnMessage.failed(feignMessage.getReason());
            return returnMessage;
        }
        Dbinfo feignDbinfo = feignMessage.getData();
        String url = feignDbinfo.getUrl();
        String username = feignDbinfo.getUsername();
        String password = feignDbinfo.getPassword();

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            String sql = "";
            if(isOrderReverse){
                sql = "SELECT "+taskField+" FROM "+tableName+" ORDER BY "+orderField+" DESC";
            }else{
                sql = "SELECT "+taskField+" FROM "+tableName+" ORDER BY "+orderField;
            }
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                try(ResultSet rs = ps.executeQuery()){
                    JSONArray jsonArray = new JSONArray();
                    ResultSetMetaData resultSetMetaData = rs.getMetaData();
                    int columnNum = resultSetMetaData.getColumnCount();

                    while (rs.next()) {
                        com.alibaba.fastjson.JSONObject jsonObject = new JSONObject();
                        for (int i = 1; i <= columnNum; i++) {
                            String columnName = resultSetMetaData.getColumnName(i);
                            String value = rs.getString(columnName);
                            jsonObject.put(columnName, value);
                        }
                        jsonArray.add(jsonObject);
                    }
                    returnMessage.success(jsonArray);
                }
            }
        }catch (SQLException e){
            returnMessage.failed(e.toString());
        }
        return returnMessage;
    }


    public ReturnMessage<Integer> getItemCount(DbinfoWithFieldNames dbinfoWithFieldNames){
        ReturnMessage<Integer> returnMessage = new ReturnMessage<>();

        String dbname = dbinfoWithFieldNames.getDbname();
        String tableName = dbinfoWithFieldNames.getTableName();

        ReturnMessage<Dbinfo> feignMessage = feignDatabaseManager.selectByDbname(dbname);
        if(feignMessage.getStatus().equals("failed")){
            returnMessage.failed(feignMessage.getReason());
            return returnMessage;
        }
        Dbinfo feignDbinfo = feignMessage.getData();
        String url = feignDbinfo.getUrl();
        String username = feignDbinfo.getUsername();
        String password = feignDbinfo.getPassword();

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            String sql = "SELECT * FROM "+tableName;
            try(PreparedStatement ps = conn.prepareStatement(sql)){
                try(ResultSet rs = ps.executeQuery()){
                    ResultSetMetaData rsMetaData = rs.getMetaData();
                    returnMessage.success(rsMetaData.getColumnCount());
                }
            }
        }catch (SQLException e){
            returnMessage.failed("DatabaseConn ApiService SQL语句:"+e.toString());
        }
        return returnMessage;
    }
}

