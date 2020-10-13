package com.zyv1.databasemanager.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Dbinfo {
    private String url;
    private String username;
    private String password;
    private String dbname;
    private String tableStr;//使用逗号分割存于数据库
}
