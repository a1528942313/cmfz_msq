package com.baizhi.service;

import com.baizhi.api.BaseApiService;
import com.baizhi.dao.AlbumDAO;
import com.baizhi.dao.ChapterDAO;
import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import com.baizhi.entity.Chart;
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
 * 类描述信息 (章节业务实现类)
 *
 * @author : maoshuqi
 * @date : 2019-10-28
 * @version: V_1.0.0
 */
@Service
@Transactional
public class ChapterServiceImpl extends BaseApiService implements ChapterService {

    @Autowired
    private ChapterDAO chapterDAO;
    @Autowired
    private AlbumDAO albumDAO;

    /**
     * 方法描述: (分页展示专辑下的所有章节)
     *
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author : maoshuqi
     * @date : 2019-10-28
     * @version: V_1.0.0
     */
    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public Map<String, Object> showAll(Integer page, Integer rows, Chapter chapter) {
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<Chapter> chapters = chapterDAO.selectByRowBounds(chapter, rowBounds);
        int totalcount = chapterDAO.selectCount(chapter);
        int  pageCount=totalcount%rows==0?totalcount/rows:totalcount/rows+1;
        return setResultSuccessDataByPage(chapters,page,pageCount,totalcount);
    }

    /**
     * 方法描述: (添加章节)
     *
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author : maoshuqi
     * @date : 2019-10-28
     * @version: V_1.0.0
     */
    @Override
    public Map<String, Object> insert(Chapter chapter) {
        chapter.setId(UUID.randomUUID().toString());
        chapter.setCreateDate(new Date());
        int i = chapterDAO.insert(chapter);
        if(i==0) return setResultError("添加失败");
        Album album = albumDAO.selectByPrimaryKey(chapter.getAlbumId());
        Integer chapterCount = album.getChapterCount();
        album.setChapterCount(chapterCount+1);
        int update = albumDAO.updateByPrimaryKeySelective(album);
        if(update==0) return setResultError("修改集数失败");
        return setResultSuccessData(chapter);
    }

    /**
     * 方法描述: (修改章节)
     *
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author : maoshuqi
     * @date : 2019-10-28
     * @version: V_1.0.0
     */
    @Override
    public Map<String, Object> update(Chapter chapter) {
        chapter.setLastUpdateDate(new Date());
        int i = chapterDAO.updateByPrimaryKeySelective(chapter);
        if(i==0) return setResultError("修改失败");
        return setResultSuccessData(chapter);
    }

    /**
     * 方法描述: (删除章节)
     *
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author : maoshuqi
     * @date : 2019-10-28
     * @version: V_1.0.0
     */
    @Override
    public void delete(Chapter chapter) {
        chapterDAO.deleteByPrimaryKey(chapter.getId());
        Album album = albumDAO.selectByPrimaryKey(chapter.getAlbumId());
        Integer chapterCount = album.getChapterCount();
        album.setChapterCount(chapterCount-1);
        albumDAO.updateByPrimaryKeySelective(album);
    }
}
