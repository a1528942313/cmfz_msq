package com.baizhi.service;

import com.baizhi.api.BaseApiService;
import com.baizhi.dao.GuruDAO;
import com.baizhi.dao.UserDAO;
import com.baizhi.entity.Admin;
import com.baizhi.entity.Chart;
import com.baizhi.entity.Guru;
import com.baizhi.entity.User;
import io.goeasy.GoEasy;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * 类描述信息 (用户业务实现类)
 *
 * @author : maoshuqi
 * @date : 2019-10-29
 * @version: V_1.0.0
 */
@Service
@Transactional
public class UserServiceImpl extends BaseApiService implements UserService{

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private GuruDAO guruDAO;
    @Autowired
    private HttpSession session;

    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public Map<String, Object> showAll(Integer page, Integer rows) {
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<User> users = userDAO.selectByRowBounds(null, rowBounds);
        int totalcount = userDAO.selectCount(null);
        int  pageCount=totalcount%rows==0?totalcount/rows:totalcount/rows+1;
        return setResultSuccessDataByPage(users,page,pageCount,totalcount);
    }

    @Override
    public Map<String, Object> insert(User user) {
        user.setCreateDate(new Date());
        user.setId(UUID.randomUUID().toString());
        Guru guru = guruDAO.selectByPrimaryKey(user.getGuruId());
        user.setGuruImage(guru.getImage());
        int i = userDAO.insertSelective(user);
        if(i==0) return setResultError("添加失败");
        GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-df17106e996e4cc6884b52ae567d4389");
        goEasy.publish("cmfz_msq", "用户添加成功");
        return setResultSuccessData(user);
    }

    @Override
    public Map<String, Object> update(User user) {
        if(user.getGuruId()!=null) {
            Guru guru = guruDAO.selectByPrimaryKey(user.getGuruId());
            user.setGuruImage(guru.getImage());
        }
        user.setLastUpdateDate(new Date());
        int i = userDAO.updateByPrimaryKeySelective(user);
        if(i==0)return setResultError("修改失败");
        return setResultSuccessData(user);
    }

    @Override
    public void delete(String id) {
        userDAO.deleteByPrimaryKey(id);
    }

    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public List<Object> selectProvinceCount(String sex) {
        List<Object> list = new ArrayList<>();
        List<User> users = userDAO.selectProvinceCount(sex);
        for (User user : users) {
            Map<String, Object> map = new HashMap<>();
            map.put("name",user.getProvince());
            map.put("value",user.getNumber());
            list.add(map);
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public List<Integer> selectDateCount(String sex) {
        List<Integer> integers = new ArrayList<>();
        Integer user7 = userDAO.selectDateCount(sex,7);
        Integer user14 = userDAO.selectDateCount(sex,14);
        Integer user21 = userDAO.selectDateCount(sex,21);
        integers.add(user7);
        integers.add(user14);
        integers.add(user21);
        return integers;
    }
    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public void login(User user,String code) {
        String sessionCode = (String)session.getAttribute("code");
        if(!code.equalsIgnoreCase(sessionCode))throw new RuntimeException("验证码错误");
        User login = userDAO.selectOne(user);
        if(login==null)throw new RuntimeException("用户名或密码错误");
        session.setAttribute("loginAdmin",login);
    }
}
