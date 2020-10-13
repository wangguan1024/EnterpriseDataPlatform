package com.zyv1.databaseconn.bean;

import lombok.Data;

@Data
public class Dbinfo {
    private String url;
    private String username;
    private String password;
    private String dbname;
    private String tableStr;//使用逗号分割存于数据库
}
