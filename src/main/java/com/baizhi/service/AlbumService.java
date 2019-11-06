package com.baizhi.service;

import com.baizhi.entity.Album;

import java.util.List;
import java.util.Map;

public interface AlbumService {

    public Map<String, Object> showAll(Integer page, Integer rows);
    public List<Album> selectAll();
    public Map<String,Object> insert(Album album);
    public Map<String,Object> update(Album album);
    public void delete(String id);
}
