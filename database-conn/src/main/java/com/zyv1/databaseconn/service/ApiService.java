package com.zyv1.databaseconn.service;


import com.zyv1.databaseconn.util.ReturnMessage;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class ApiService {
    public ReturnMessage<String> AutoGetTableNames(String url, String username, String password)  {
        ReturnMessage<String> returnMessage = new ReturnMessage<>();
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
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
}
