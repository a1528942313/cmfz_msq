package com.baizhi.service;


import com.baizhi.entity.Guru;

import java.util.List;
import java.util.Map;

/**
 * 接口描述信息 (上师业务接口)
 *
 * @author : maoshuqi
 * @date : 2019-10-27 15:32
 * @version: V_1.0.0
 */
public interface GuruService {

    public Map<String, Object> showAll(Integer page, Integer rows);
    public List<Guru> selectAll();
    public Map<String,Object> insert(Guru guru);
    public Map<String,Object> update(Guru guru);
    public void delete(String id);

}
