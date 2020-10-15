package com.zyv1.databaseconn.api;

import lombok.Data;

@Data
public class DbinfoWithFieldNames {
    private String dbname;
    private String tableName;
    private String orderField;
    private Boolean isOrderReverse;
    private String taskField;
}
