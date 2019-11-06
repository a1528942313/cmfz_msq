package com.baizhi.service;

import com.baizhi.entity.Chart;
import java.util.Map;

/**
 * 接口描述信息 (轮播图业务接口)
 *
 * @author : maoshuqi
 * @date : 2019-10-27 15:32
 * @version: V_1.0.0
 */
public interface ChartService {

    public Map<String, Object> showAll(Integer page, Integer rows);
    public Map<String,Object> insert(Chart chart);
    public Map<String,Object> update(Chart chart);
    public void delete(String id);

}
