package com.zyv1.databaseconn.bean;

import lombok.Data;

@Data
public class Dbinfo {
    private String url;
    private String username;
    private String password;
    private String dbname;
}
