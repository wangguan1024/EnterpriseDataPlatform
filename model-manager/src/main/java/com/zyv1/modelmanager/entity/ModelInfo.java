package com.zyv1.modelmanager.entity;

import lombok.Data;

@Data
public class ModelInfo {
    private Integer id;
    private String modelName;
    private String modelLanguage;
    private String trainUrl;
    private String predictUrl;
}
