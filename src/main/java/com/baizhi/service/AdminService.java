package com.baizhi.service;

import com.baizhi.entity.Admin;

/**
 * 接口描述信息 (管理员业务接口)
 *
 * @author : maoshuqi
 * @date : 2019-10-27 15:32
 * @version: V_1.0.0
 */
public interface AdminService {

    public void login(Admin admin,String code);

}
