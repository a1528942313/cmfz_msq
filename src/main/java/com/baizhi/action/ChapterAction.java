package com.baizhi.action;

import com.baizhi.entity.Chapter;
import com.baizhi.service.ChapterService;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.MultimediaInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("Chapter")
public class ChapterAction {

    @Autowired
    private ChapterService chapterService;


    /**
     * 方法描述: (分页展示章节)
     *
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author : maoshuqi
     * @date : 2019-10-28
     * @version: V_1.0.0
     */
    @RequestMapping("showAll")
    public Map<String,Object> showAll(Integer page, Integer rows,Chapter chapter){
        Map<String, Object> map = chapterService.showAll(page, rows, chapter);
        return map;
    }


    /**
     * 方法描述: (章节的添加，修改，删除)
     *
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author : maoshuqi
     * @date : 2019-10-28
     * @version: V_1.0.0
     */
    @RequestMapping("edit")
    public Map<String,Object> edit(String oper, Chapter chapter) {
        if("del".equals(oper)){
            chapterService.delete(chapter);
        }else if("add".equals(oper)){
            Map<String, Object> map = chapterService.insert(chapter);
            return map;
        }else if("edit".equals(oper)){
            Map<String, Object> map = chapterService.update(chapter);
            return map;
        }
        return null;
    }

    /**
     * 方法描述: (上传文件)
     *
     * @author : maoshuqi
     * @date : 2019-10-28
     * @version: V_1.0.0
     */
    @RequestMapping("upload")
    public void upload(MultipartFile upload, HttpSession session, Chapter chapter) throws  Exception{
        //获取文件上传到的文件夹绝对路径
        String realPath = session.getServletContext().getRealPath("/statics/album/audition");
        String fileName = upload.getOriginalFilename();
        //在目标文件夹下创建于上传文件名相同的文件
        File file = new File(realPath + "/" + fileName);
        //把接收到的文件上传至空文件    文件上传
        if(!file.exists()) upload.transferTo(file);
        if(!upload.getOriginalFilename().isEmpty()){
            Encoder encoder = new Encoder();
            MultimediaInfo m = encoder.getInfo(file);
            //文件大小
            double size = new BigDecimal(file.length()).divide(new BigDecimal(1024)).divide(new BigDecimal(1024)).setScale(2, BigDecimal.ROUND_UP).doubleValue();
            chapter.setSize(size);
            //文件时长   s
            long ls = m.getDuration()/1000;
            String fen = ls/60>=10?ls/60+"":"0"+ls/60;
            String miao = ls%60>=10?ls%60+"":"0"+ls%60;
            chapter.setDuration(fen+":"+miao);
            chapter.setUrl(fileName);
        }
        chapterService.update(chapter);
    }
}
