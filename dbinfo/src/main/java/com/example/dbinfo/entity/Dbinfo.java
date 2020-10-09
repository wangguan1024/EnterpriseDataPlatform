package com.example.dbinfo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class Dbinfo {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String url;
    private String username;
    private String password;
    private String dbname;
    private String tableStr;//使用逗号分割存于数据库
}
