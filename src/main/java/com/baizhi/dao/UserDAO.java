package com.baizhi.dao;

import com.baizhi.entity.User;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserDAO extends Mapper<User> {
    public List<User> selectProvinceCount(String sex);
    public Integer selectDateCount(@Param("sex") String sex, @Param("date") Integer date);
}
