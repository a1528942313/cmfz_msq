package com.baizhi.action;


import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("User")
public class UserAction {

    @Autowired
    private UserService userService;

    @RequestMapping("showAll")
    public Map<String,Object> showAll(Integer page, Integer rows){
        Map<String, Object> stringObjectMap = userService.showAll(page, rows);
        return stringObjectMap;
    }

    @RequestMapping("selectProvinceCount")
    public Map<String,Object> selectProvinceCount(){
        Map<String, Object> map = new HashMap<>();
        List<Object> listM = userService.selectProvinceCount("M");
        List<Object> listF = userService.selectProvinceCount("F");
        map.put("M",listM);
        map.put("F",listF);
        return map;
    }

    @RequestMapping("selectDateCount")
    public Map<String,Object> selectDateCount(){
        Map<String, Object> map = new HashMap<>();
        List<Integer> listM = userService.selectDateCount("M");
        List<Integer> listF = userService.selectDateCount("F");
        map.put("M",listM);
        map.put("F",listF);
        return map;
    }

    @RequestMapping("edit")
    public Map<String,Object> edit(String oper, User user) {
        if("add".equals(oper)){
            Map<String, Object> map = userService.insert(user);
            return map;
        }
        if("edit".equals(oper)){
            Map<String, Object> map = userService.update(user);
            return map;
        }
        if("del".equals(oper)){
            userService.delete(user.getId());
        }
        return null;
    }

    @RequestMapping("upload")
    public void upload(MultipartFile upload, HttpSession session, User user) throws  Exception{
        //获取文件上传到的文件夹绝对路径
        String realPath = session.getServletContext().getRealPath("/statics/user/image");
        String fileName = upload.getOriginalFilename();
        //在目标文件夹下创建于上传文件名相同的文件
        File file = new File(realPath + "/" + fileName);
        //把接收到的文件上传至空文件    文件上传
        if(!file.exists()) upload.transferTo(file);
        if(!upload.getOriginalFilename().isEmpty()) user.setImage(fileName);
        userService.update(user);
    }

}

