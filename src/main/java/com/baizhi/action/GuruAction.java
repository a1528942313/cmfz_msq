package com.baizhi.action;


import com.baizhi.entity.Guru;
import com.baizhi.service.GuruService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("Guru")
public class GuruAction {

    @Autowired
    private GuruService guruService;

    @RequestMapping("showAll")
    public Map<String,Object> showAll(Integer page, Integer rows){
        Map<String, Object> stringObjectMap = guruService.showAll(page, rows);
        return stringObjectMap;
    }

    @RequestMapping("selectAll")
    public void selectAll(HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        StringBuilder sb = new StringBuilder();
        sb.append("<select id='guruId' name='guruId' class='form-control'>");
        List<Guru> gurus = guruService.selectAll();
        for (Guru guru : gurus) {
            sb.append("<option value="+guru.getId()+">"+guru.getName()+"</option>");
        }
        sb.append("</select>");
        out.print(sb);
        out.close();
    }


    @RequestMapping("edit")
    public Map<String,Object> edit(String oper, Guru guru) {
        if("add".equals(oper)){
            Map<String, Object> map = guruService.insert(guru);
            return map;
        }
        if("edit".equals(oper)){
            Map<String, Object> map = guruService.update(guru);
            return map;
        }
        if("del".equals(oper)){
            guruService.delete(guru.getId());
        }
        return null;
    }

    @RequestMapping("upload")
    public void upload(MultipartFile upload, HttpSession session, Guru guru) throws  Exception{
        //获取文件上传到的文件夹绝对路径
        String realPath = session.getServletContext().getRealPath("/statics/guru/image");
        String fileName = upload.getOriginalFilename();
        //在目标文件夹下创建于上传文件名相同的文件
        File file = new File(realPath + "/" + fileName);
        //把接收到的文件上传至空文件    文件上传
        if(!file.exists()) upload.transferTo(file);
        if(!upload.getOriginalFilename().isEmpty()) guru.setImage(fileName);
        guruService.update(guru);
    }

}

