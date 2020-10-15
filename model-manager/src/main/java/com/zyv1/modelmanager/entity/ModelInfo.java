package com.zyv1.modelmanager.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class ModelInfo {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String modelName;
    private String modelLanguage;
    private String trainUrl;
    private String predictUrl;
}
