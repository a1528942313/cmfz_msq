package com.baizhi.service;

import com.baizhi.entity.Chapter;

import java.util.Map;

public interface ChapterService {

    public Map<String, Object> showAll(Integer page, Integer rows,Chapter chapter);
    public Map<String,Object> insert(Chapter chapter);
    public Map<String,Object> update(Chapter chapter);
    public void delete(Chapter chapter);
}
