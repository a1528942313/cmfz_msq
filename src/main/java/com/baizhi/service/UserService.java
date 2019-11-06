package com.baizhi.service;

import com.baizhi.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    public Map<String, Object> showAll(Integer page, Integer rows);
    public Map<String,Object> insert(User user);
    public Map<String,Object> update(User user);
    public void login(User user,String code);
    public void delete(String id);
    public List<Object> selectProvinceCount(String sex);
    public List<Integer> selectDateCount(String sex);
}
