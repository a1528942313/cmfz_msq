package com.baizhi.service;

import com.baizhi.dao.ChartDAO;
import com.baizhi.entity.Chart;
import com.baizhi.api.BaseApiService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 类描述信息 (轮播图业务实现类)
 *
 * @author : maoshuqi
 * @date : 2019-10-27 15:32
 * @version: V_1.0.0
 */
@Service
@Transactional
public class ChartServiceImpl extends BaseApiService implements ChartService {

    @Autowired
    private ChartDAO chartDAO;

    /**
     * 方法描述: (分页展示轮播图)
     *
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author : maoshuqi
     * @date : 2019-10-27 15:32
     * @version: V_1.0.0
     */
    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public Map<String, Object> showAll(Integer page, Integer rows) {
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<Chart> charts = chartDAO.selectByRowBounds(null,rowBounds);
        int totalcount = chartDAO.selectCount(null);
        int  pageCount=totalcount%rows==0?totalcount/rows:totalcount/rows+1;
        return setResultSuccessDataByPage(charts,page,pageCount,totalcount);
    }

    /**
     * 方法描述: (添加轮播图)
     *
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author : maoshuqi
     * @date : 2019-10-27 15:32
     * @version: V_1.0.0
     */
    @Override
    public Map<String,Object> insert(Chart chart){
        chart.setId(UUID.randomUUID().toString());
        chart.setCreateDate(new Date());
        int i = chartDAO.insertSelective(chart);
        if(i==0) return setResultError("添加失败");
        return setResultSuccessData(chart);
    }

    /**
     * 方法描述: (修改轮播图)
     *
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author : maoshuqi
     * @date : 2019-10-27 15:32
     * @version: V_1.0.0
     */
    @Override
    public Map<String,Object> update(Chart chart) {
        chart.setLastUpdateDate(new Date());
        int i = chartDAO.updateByPrimaryKeySelective(chart);
        if(i==0) return setResultError("修改失败");
        return setResultSuccessData(chart);
    }

    /**
     * 方法描述: (删除轮播图)
     *
     * @author : maoshuqi
     * @date : 2019-10-27 15:32
     * @version: V_1.0.0
     */
    @Override
    public void delete(String id) {
        chartDAO.deleteByPrimaryKey(id);
    }

}
