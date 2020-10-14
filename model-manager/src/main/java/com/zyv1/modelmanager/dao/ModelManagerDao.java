package com.zyv1.modelmanager.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyv1.modelmanager.entity.ModelInfo;
import org.springframework.stereotype.Repository;


@Repository
public interface ModelManagerDao extends BaseMapper<ModelInfo> {

}
