package com.zyv1.modelinvoking.entity;

import lombok.Data;

@Data
public class ModelRelData {

    //获取模型详细信息
    private String modelName;

    //查询数据用
    private String dbname;
    private String tableName;
    private String orderField;
    private Boolean isOrderReverse;
    private String taskField;

}
