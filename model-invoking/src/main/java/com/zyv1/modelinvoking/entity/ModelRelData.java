package com.zyv1.modelinvoking.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class ModelRelData{
    private Integer id;
    //关联模型信息
    private String modelName;

    //关联模型的输入数据
    private String dbname;
    private String tableName;
    private String orderField;
    private Boolean isOrderReverse = false;
    private String taskField;

    //预警数据与用户设定值比对业务相关数据
    private Integer itemNum = 0;
    private Double warnLine;
    private Boolean isBigger = true;

}
