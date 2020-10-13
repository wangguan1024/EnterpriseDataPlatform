package com.zyv1.databaseconn.service;



import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.zyv1.databaseconn.bean.Dbinfo;
import com.zyv1.databaseconn.feign.FeignDatabaseManager;
import com.zyv1.databaseconn.util.ReturnMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service
public class SelectService {

    @Autowired
    private FeignDatabaseManager feignDatabaseManager;

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
