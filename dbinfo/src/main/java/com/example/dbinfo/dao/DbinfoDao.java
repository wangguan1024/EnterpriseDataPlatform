package com.example.dbinfo.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.dbinfo.entity.Dbinfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface DbinfoDao extends BaseMapper<Dbinfo> {

}
