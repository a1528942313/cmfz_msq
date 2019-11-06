package com.baizhi.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 类描述信息 (拦截器配置)
 *
 * @author : maoshuqi
 * @date : 2019-10-27 15:32
 * @version: V_1.0.0
 */
@Component
public class InterceptorConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private LoginAdminInterceptor loginAdminInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginAdminInterceptor).addPathPatterns("/**").excludePathPatterns("/Admin/**");
    }
}
