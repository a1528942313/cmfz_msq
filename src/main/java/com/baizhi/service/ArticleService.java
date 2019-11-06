package com.baizhi.service;

import com.baizhi.entity.Article;

import java.util.Map;

public interface ArticleService {

    public Map<String, Object> showAll(Integer page, Integer rows);
    public Map<String,Object> insert(Article article);
    public Map<String,Object> update(Article article);
    public void delete(String id);
}
