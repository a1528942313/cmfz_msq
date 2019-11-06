package com.baizhi.action;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.entity.Album;
import com.baizhi.service.AlbumService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("Album")
public class AlbumAction {

    @Autowired
    private AlbumService albumService;


    /**
     * 方法描述: (分页展示专辑)
     *
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author : maoshuqi
     * @date : 2019-10-28
     * @version: V_1.0.0
     */
    @RequestMapping("showAll")
    public Map<String,Object> showAll(Integer page, Integer rows){
        Map<String, Object> map = albumService.showAll(page, rows);
        return map;
    }


    /**
     * 方法描述: (专辑的添加，修改，删除)
     *
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author : maoshuqi
     * @date : 2019-10-28
     * @version: V_1.0.0
     */
    @RequestMapping("edit")
    public Map<String,Object> edit(String oper, Album album) {
        if("del".equals(oper)){
            albumService.delete(album.getId());
        }else if("edit".equals(oper)){
            Map<String, Object> map = albumService.update(album);
            return map;
        }else if("add".equals(oper)){
            Map<String, Object> map = albumService.insert(album);
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
    public void upload(MultipartFile upload, HttpSession session, Album album) throws IOException {
        //获取文件上传到的文件夹绝对路径
        String realPath = session.getServletContext().getRealPath("/statics/album/image");
        //在目标文件夹下创建于上传文件名相同的文件
        File file = new File(realPath + "/" + upload.getOriginalFilename());
        //把接收到的文件上传至空文件    文件上传
        if(!file.exists()) upload.transferTo(file);
        if(!upload.getOriginalFilename().isEmpty())album.setImage(upload.getOriginalFilename());
        albumService.update(album);
    }

    /**
     * 方法描述: (上传文件)
     *
     * @author : maoshuqi
     * @date : 2019-10-31
     * @version: V_1.0.0
     */
    @RequestMapping("export")
    public void export(HttpServletResponse response) throws IOException {
        List<Album> list = albumService.selectAll();
        ExportParams exportParams = new ExportParams("专辑以及章节", "章节");
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, Album.class, list);
        String fileName = URLEncoder.encode("专辑表.xls","UTF-8");
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("content-disposition","attachment;filename=" + fileName);
        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
