package com.baizhi.action;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.baizhi.dao.*;
import com.baizhi.entity.*;
import com.baizhi.service.UserService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
@RequestMapping("JSON")
public class JSONAction {

    @Autowired
    private AlbumDAO albumDAO;

    @Autowired
    private ArticleDAO articleDAO;

    @Autowired
    private ChapterDAO chapterDAO;

    @Autowired
    private ChartDAO chartDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserService userService;

    @RequestMapping("firstPage")
    public Map<String,Object> firstPage(Article article,String type,String sub_type){
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> body = new HashMap<>();
        Example exampleAlbum = new Example(Album.class);
        exampleAlbum.setOrderByClause("create_date desc");
        RowBounds rowBoundsAlbum = new RowBounds(0, 6);
        List<Album> albums = albumDAO.selectByExampleAndRowBounds(exampleAlbum,rowBoundsAlbum);
        try {
            if ("all".equals(type)) {
                Example exampleChart = new Example(Chart.class);
                exampleChart.setOrderByClause("create_date desc");
                RowBounds rowBoundsChart = new RowBounds(0, 5);
                List<Chart> charts = chartDAO.selectByExampleAndRowBounds(exampleChart,rowBoundsChart);
                Example exampleArticle = new Example(Article.class);
                exampleArticle.setOrderByClause("create_date desc");
                RowBounds rowBoundsArticels= new RowBounds(0, 2);
                List<Article> articles = articleDAO.selectByExampleAndRowBounds(exampleArticle,rowBoundsArticels);
                body.put("albums", albums);
                body.put("articles", articles);
                map.put("code", 200);
                map.put("header", charts);
                map.put("body", body);
            }
            if ("wen".equals(type)) {
                List<Album> albumsAll = albumDAO.selectAll();
                body.put("albums", albumsAll);
                map.put("code", 200);
                map.put("body", body);
            }
            if ("si".equals(type)) {
                if ("ssyj".equals(sub_type)) {
                    List<Article> articles = articleDAO.select(article);
                    body.put("articles", articles);
                    map.put("code", 200);
                    map.put("body", body);
                }
                if ("xmfy".equals(sub_type)) {
                    List<Article> articles = articleDAO.selectAll();
                    body.put("articles", articles);
                    map.put("code", 200);
                    map.put("body", body);
                }

            }
        }catch( Exception e ){
            e.printStackTrace();
            map.put("code", 500);
            map.put("msg", "参数错误");
        }
        return map;
    }

    @RequestMapping("si")
    public Map<String,Object> si(String id){
        Map<String, Object> map = new HashMap<>();
        try {
            Article article = articleDAO.selectByPrimaryKey(id);
            map.put("article", article);
            map.put("code", 200);
        }catch( Exception e ){
            e.printStackTrace();
            map.put("code", 500);
            map.put("msg", "参数错误");
        }
        return map;
    }

    @RequestMapping("wen")
    public Map<String,Object> wen(String id){
        Map<String, Object> map = new HashMap<>();

        try {
            Album album = albumDAO.selectByPrimaryKey(id);
            Chapter chapter = new Chapter();
            chapter.setAlbumId(id);
            List<Chapter> chapters = chapterDAO.select(chapter);
            map.put("introduction", album);
            map.put("list", chapters);
            map.put("code", 200);
        }catch( Exception e ){
            e.printStackTrace();
            map.put("code", 500);
            map.put("msg", "参数错误");
        }
        return map;
    }

    @RequestMapping("login")
    public Map<String,Object> login(User user,String code){
        Map<String, Object> map = new HashMap<>();
        try {
            userService.login(user,code);
            map.put("code", 200);
        }catch( Exception e ){
            e.printStackTrace();
            map.put("error", -200);
            map.put("errmsg","密码错误");
        }
        return map;
    }

    @RequestMapping("register")
    public Map<String,Object> register(User user){
        Map<String, Object> map = new HashMap<>();
        try {
            user.setId(UUID.randomUUID().toString());
            userDAO.insert(user);
            map.put("code", 200);
            map.put("user", user);
        }catch( Exception e ){
            e.printStackTrace();
            map.put("error", -200);
            map.put("errmsg","该手机号已经存在");
        }
        return map;
    }

    @RequestMapping("update")
    public Map<String,Object> update(User user){
        Map<String, Object> map = new HashMap<>();
        try {
            userDAO.updateByPrimaryKey(user);
            map.put("code", 200);
            map.put("user", user);
        }catch( Exception e ){
            e.printStackTrace();
            map.put("error", -200);
            map.put("errmsg","该手机号已经存在");
        }
        return map;
    }


    @RequestMapping("selectUser")
    public Map<String,Object> selectUser(){
        Map<String, Object> map = new HashMap<>();
        try {
            List<User> users = userDAO.selectAll();
            map.put("code", 200);
            map.put("list", users);
        }catch( Exception e ){
            e.printStackTrace();
            map.put("error", -200);
            map.put("errmsg", "用户列表为空");
            return map;
        }
        return map;
    }

    @RequestMapping("obtain")
    public void obtain(HttpSession session,String phone){
        Random random = new Random();
        String str = "";
        for (int i = 0; i < 6; i++) {
            str +=random.nextInt(10);
        }
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4FjrNdd1enCruvsgSFRf", "EJC3zTJv9MaGAzj0w58h51WFicNKWi");
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "衣心依意");
        request.putQueryParameter("TemplateCode", "SMS_17693667");//SMS_176936671
        request.putQueryParameter("TemplateParam", "{\"code\":"+str+"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        session.setAttribute("code",str);
    }

    @RequestMapping("check")
    public Map<String,Object> check(HttpSession session,String code){
        Map<String, Object> map = new HashMap<>();
        String attribute = (String)session.getAttribute("code");
        if(code.equals(attribute))map.put("result", "success");
        else map.put("result","error");
        return map;
    }

}
