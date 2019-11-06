package com.baizhi.action;

import com.baizhi.entity.Chart;
import com.baizhi.service.ChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Map;

/**
 * 类描述信息 (轮播图控制器类)
 *
 * @author : maoshuqi
 * @date : 2019-10-27 15:32
 * @version: V_1.0.0
 */
@RestController
@RequestMapping("Chart")
public class ChartAction {

    @Autowired
    private ChartService chartService;

    /**
     * 方法描述: (分页展示轮播图)
     *
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author : maoshuqi
     * @date : 2019-10-27 15:32
     * @version: V_1.0.0
     */
    @RequestMapping("showAll")
    public Map<String,Object> showAll(Integer page, Integer rows){
        Map<String, Object> showAll = chartService.showAll(page, rows);
        return showAll;
    }

    /**
     * 方法描述: (轮播图的添加，修改，删除)
     *
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author : maoshuqi
     * @date : 2019-10-27 15:32
     * @version: V_1.0.0
     */
    @RequestMapping("edit")
    public Map<String,Object> edit(String oper, Chart chart) throws  Exception{
        if ("add".equals(oper)){
            Map<String, Object> map = chartService.insert(chart);
            return map;
        }else if("edit".equals(oper)){
            Map<String, Object> map = chartService.update(chart);
            return map;
        }else if("del".equals(oper)){
            chartService.delete(chart.getId());
        }
        return null;
    }

    /**
     * 方法描述: (上传文件)
     *
     * @author : maoshuqi
     * @date : 2019-10-27 15:32
     * @version: V_1.0.0
     */
    @RequestMapping("upload")
    public void upload(MultipartFile upload, HttpSession session,Chart chart) throws  Exception{
        //获取文件上传到的文件夹绝对路径
        String realPath = session.getServletContext().getRealPath("/statics/chart/image");
        //在目标文件夹下创建于上传文件名相同的文件
        File file = new File(realPath + "/" + upload.getOriginalFilename());
        //把接收到的文件上传至空文件    文件上传
        if(!file.exists()) upload.transferTo(file);
        if(!upload.getOriginalFilename().isEmpty()) chart.setImage(upload.getOriginalFilename());
        chartService.update(chart);

    }
}
