package com.baizhi.service;

import com.baizhi.dao.AdminDAO;
import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

/**
 * 类描述信息 (管理员的业务实现类)
 *
 * @author : maoshuqi
 * @date : 2019-10-27 15:32
 * @version: V_1.0.0
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminDAO adminDAO;
    @Autowired
    private HttpSession session;

    /**
     * 方法描述: (查询用户名登录)
     *
     * @author : maoshuqi
     * @date : 2019-10-27 15:32
     * @version: V_1.0.0
     */
    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public void login(Admin admin, String code) {
        String sessionCode = (String)session.getAttribute("code");
        if(!code.equalsIgnoreCase(sessionCode))throw new RuntimeException("验证码错误");
        Admin login = adminDAO.selectOne(admin);
        if(login==null)throw new RuntimeException("用户名或密码错误");
        session.setAttribute("loginAdmin",login);
    }
}
