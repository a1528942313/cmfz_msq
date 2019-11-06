package com.baizhi.service;

import com.baizhi.api.BaseApiService;
import com.baizhi.dao.ArticleDAO;
import com.baizhi.dao.GuruDAO;
import com.baizhi.entity.Article;
import com.baizhi.entity.Guru;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 类描述信息 (文章业务实现类)
 *
 * @author : maoshuqi
 * @date : 2019-10-29
 * @version: V_1.0.0
 */
@Service
@Transactional
public class ArticleServiceImpl extends BaseApiService implements ArticleService {

    @Autowired
    private ArticleDAO articleDAO;
    @Autowired
    private GuruDAO guruDAO;
    @Override
    public Map<String, Object> showAll(Integer page, Integer rows) {
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<Article> articles = articleDAO.selectByRowBounds(null, rowBounds);
        int totalcount = articleDAO.selectCount(null);
        int  pageCount=totalcount%rows==0?totalcount/rows:totalcount/rows+1;
        return setResultSuccessDataByPage(articles,page,pageCount,totalcount);
    }

    @Override
    public Map<String, Object> insert(Article article) {
        article.setId(UUID.randomUUID().toString());
        article.setCreateDate(new Date());
        Guru guru = guruDAO.selectByPrimaryKey(article.getGuruId());
        article.setGuruImage(guru.getImage());
        int insert = articleDAO.insert(article);
        if(insert==0) return setResultError("添加失败");
        return setResultSuccessData(article);
    }

    @Override
    public Map<String, Object> update(Article article) {
        article.setLastUpdateDate(new Date());
        if(article.getGuruId()!=null) {
            Guru guru = guruDAO.selectByPrimaryKey(article.getGuruId());
            article.setGuruImage(guru.getImage());
        }
        int i = articleDAO.updateByPrimaryKeySelective(article);
        if(i==0)return setResultError("修改失败");
        return setResultSuccessData(article);
    }

    @Override
    public void delete(String id) {
        articleDAO.deleteByPrimaryKey(id);
    }
}
