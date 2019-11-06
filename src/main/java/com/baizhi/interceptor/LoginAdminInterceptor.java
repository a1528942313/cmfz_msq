package com.baizhi.interceptor;

import com.baizhi.entity.Admin;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 类描述信息 (管理员的拦截器书写)
 *
 * @author : maoshuqi
 * @date : 2019-10-27 15:32
 * @version: V_1.0.0
 */
@Component
public class LoginAdminInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Admin login = (Admin) request.getSession().getAttribute("loginAdmin");
        if(login!=null){
            return true;
        }
        response.sendRedirect(request.getContextPath()+"/login/login.jsp");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
