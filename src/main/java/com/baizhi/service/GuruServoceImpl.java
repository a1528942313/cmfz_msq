package com.baizhi.service;

import com.baizhi.api.BaseApiService;
import com.baizhi.dao.GuruDAO;
import com.baizhi.entity.Guru;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class GuruServoceImpl extends BaseApiService implements GuruService {

    @Autowired
    private GuruDAO guruDAO;

    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public Map<String, Object> showAll(Integer page, Integer rows) {
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<Guru> albums = guruDAO.selectByRowBounds(null, rowBounds);
        int totalcount = guruDAO.selectCount(null);
        int  pageCount=totalcount%rows==0?totalcount/rows:totalcount/rows+1;
        return setResultSuccessDataByPage(albums,page,pageCount,totalcount);
    }

    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public List<Guru> selectAll() {
        List<Guru> gurus = guruDAO.selectAll();
        return gurus;
    }

    @Override
    public Map<String, Object> insert(Guru guru) {
        guru.setCreateDate(new Date());
        guru.setId(UUID.randomUUID().toString());
        int insert = guruDAO.insert(guru);
        if(insert==0) return setResultError("添加失败");
        return setResultSuccessData(guru);
    }

    @Override
    public Map<String, Object> update(Guru guru) {
        guru.setLastUpdateDate(new Date());
        int i = guruDAO.updateByPrimaryKeySelective(guru);
        if(i==0)return setResultError("修改失败");
        return setResultSuccessData(guru);
    }

    @Override
    public void delete(String id) {guruDAO.deleteByPrimaryKey(id);}
}
