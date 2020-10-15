package com.zyv1.warnserver.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyv1.warnserver.entity.WarnInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface WarnInfoDao extends BaseMapper<WarnInfo> {
}
