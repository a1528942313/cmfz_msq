package com.baizhi.service;

import com.baizhi.api.BaseApiService;
import com.baizhi.dao.AlbumDAO;
import com.baizhi.dao.ArticleDAO;
import com.baizhi.dao.ChapterDAO;
import com.baizhi.dao.GuruDAO;
import com.baizhi.entity.Album;
import com.baizhi.entity.Article;
import com.baizhi.entity.Chapter;
import com.baizhi.entity.Guru;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 类描述信息 (专辑业务实现类)
 *
 * @author : maoshuqi
 * @date : 2019-10-28
 * @version: V_1.0.0
 */
@Service
@Transactional
public class AlbumServiceImpl extends BaseApiService implements AlbumService {

    @Autowired
    private AlbumDAO albumDAO;
    @Autowired
    private ChapterDAO chapterDAO;
    @Autowired
    private GuruDAO guruDAO;
    @Autowired
    private HttpSession session;

    /**
     * 方法描述: (分页展示专辑)
     *
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author : maoshuqi
     * @date : 2019-10-28
     * @version: V_1.0.0
     */
    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public Map<String, Object> showAll(Integer page, Integer rows) {
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<Album> albums = albumDAO.selectByRowBounds(null, rowBounds);
        int totalcount = albumDAO.selectCount(null);
        int  pageCount=totalcount%rows==0?totalcount/rows:totalcount/rows+1;
        return setResultSuccessDataByPage(albums,page,pageCount,totalcount);
    }

    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public List<Album> selectAll() {
        List<Album> albums = albumDAO.selectAll();
        for (Album album : albums) {
            String image = "E:\\百知教育\\168后期项目\\后期项目\\IDEA\\cmfz_msq\\src\\main\\webapp\\statics\\album\\image\\"+album.getImage();
            album.setImage(image);
            Chapter chapter = new Chapter();
            chapter.setAlbumId(album.getId());
            List<Chapter> chapters = chapterDAO.select(chapter);
            for (Chapter chapter1 : chapters) {
                String duration = "E:\\百知教育\\168后期项目\\后期项目\\IDEA\\cmfz_msq\\src\\main\\webapp\\statics\\album\\image\\"+chapter1.getUrl();
                chapter1.setUrl(duration);
            }
            album.setChapters(chapters);
        }
        return albums;
    }

    /**
     * 方法描述: (添加专辑)
     *
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author : maoshuqi
     * @date : 2019-10-28
     * @version: V_1.0.0
     */
    @Override
    public Map<String, Object> insert(Album album) {
        album.setId(UUID.randomUUID().toString());
        album.setCreateDate(new Date());
        album.setChapterCount(0);
        Guru guru = guruDAO.selectByPrimaryKey(album.getGuruId());
        album.setGuruName(guru.getName());
        int i = albumDAO.insert(album);
        if(i==0) return setResultError("添加失败");
        return setResultSuccessData(album);

    }

    /**
     * 方法描述: (修改专辑)
     *
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author : maoshuqi
     * @date : 2019-10-28
     * @version: V_1.0.0
     */
    @Override
    public Map<String, Object> update(Album album) {
        System.out.println(album);
        album.setLastUpdateDate(new Date());
        if(album.getGuruId()!=null) {
            Guru guru = guruDAO.selectByPrimaryKey(album.getGuruId());
            album.setGuruName(guru.getName());
        }
        int i = albumDAO.updateByPrimaryKeySelective(album);
        if(i==0) return setResultError("修改失败");
        return setResultSuccessData(album);
    }

    /**
     * 方法描述: (删除专辑)
     *
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author : maoshuqi
     * @date : 2019-10-28
     * @version: V_1.0.0
     */
    @Override
    public void delete(String id) {
        Album album = albumDAO.selectByPrimaryKey(id);
        if(album.getChapterCount()==0)
        albumDAO.deleteByPrimaryKey(id);
    }
}
