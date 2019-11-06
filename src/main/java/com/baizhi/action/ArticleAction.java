package com.baizhi.action;


import com.baizhi.entity.Article;
import com.baizhi.service.ArticleService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("Article")
public class ArticleAction {
    @Autowired
    private ArticleService articleService;


    /**
     * 方法描述: (分页展示文章)
     *
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author : maoshuqi
     * @date : 2019-10-29
     * @version: V_1.0.0
     */
    @RequestMapping("showAll")
    public Map<String,Object> showAll(Integer page, Integer rows){
        Map<String, Object> map = articleService.showAll(page, rows);
        return map;
    }


    /**
     * 方法描述: (文章的添加，修改，删除)
     *
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author : maoshuqi
     * @date : 2019-10-29
     * @version: V_1.0.0
     */
    @RequestMapping("edit")
    public Map<String,Object> edit(String oper, Article article) {
        System.out.println(article);
        if("".equals(article.getContent())) article.setContent(null);
        if("add".equals(oper)){
            Map<String, Object> map = articleService.insert(article);
            return map;
        }
        if("edit".equals(oper)){
            Map<String, Object> map = articleService.update(article);
            return map;
        }
        if("del".equals(oper)){
            articleService.delete(article.getId());
        }
        return null;
    }

    /**
     * 方法描述: (上传图片)
     *
     * @author : maoshuqi
     * @date : 2019-10-29
     * @version: V_1.0.0++
     */
    @RequestMapping("upload")
    public Map<String,Object> upload(MultipartFile imgFile, HttpServletRequest request) throws  Exception{
        //获取文件上传到的文件夹绝对路径
        String realPath = request.getSession().getServletContext().getRealPath("/statics/article/image");
        //在目标文件夹下创建于上传文件名相同的文件
        File file = new File(realPath + "/" + imgFile.getOriginalFilename());
        //把接收到的文件上传至空文件    文件上传
        if(!file.exists()) imgFile.transferTo(file);
        HashMap<String, Object> map = new HashMap<>();
        map.put("error",0);
        map.put("url","http://"+request.getServerName()+":"+request.getServerPort()+"/"+request.getContextPath()+"/statics/article/image/"+imgFile.getOriginalFilename());
        return map;

    }

    /**
     * 方法描述: (展示图片)
     *
     * @author : maoshuqi
     * @date : 2019-10-29
     * @version: V_1.0.0++
     */
    @RequestMapping("browser")
    public Map<String,Object> browser(HttpServletRequest request ) {
        HashMap<String, Object> map = new HashMap<>();
        File[] files = new File(request.getSession().getServletContext().getRealPath("/statics/article/image")).listFiles();
        ArrayList<Object> list = new ArrayList<>();
        for (File file : files) {
            HashMap<String, Object> photo = new HashMap<>();
            photo.put("is_dir",false);
            photo.put("has_file",false);
            photo.put("filesize",file.length());
            photo.put("is_photo",true);
            photo.put("filetype", FilenameUtils.getExtension(file.getName()));
            photo.put("filename",file.getName());
            photo.put("datetime",new Date());
            list.add(photo);
        }
        map.put("current_url","http://"+request.getServerName()+":"+request.getServerPort()+"/"+request.getContextPath()+"/statics/article/image/");
        map.put("total_count",files.length);
        map.put("file_list",list);
        return map;
    }
}

