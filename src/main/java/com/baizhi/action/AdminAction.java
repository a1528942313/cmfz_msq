package com.baizhi.action;

import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import com.baizhi.util.CreateValidateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 类描述信息 (管理员控制器类)
 *
 * @author : maoshuqi
 * @date : 2019-10-27 15:32
 * @version: V_1.0.0
 */
@Controller
@RequestMapping("Admin")
public class AdminAction {

    @Autowired
    private AdminService adminService;

    /**
     * 方法描述: (登录)
     *
     * @return java.lang.String
     * @author : maoshuqi
     * @date : 2019-10-27 15:32
     * @version: V_1.0.0
     */
    @RequestMapping("login")
    public String login(Admin admin, Model model,String code){
        try {
            adminService.login(admin,code);
            return "redirect:/statics/admin.jsp";
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("message",e.getMessage());
            return "/login/login";
        }
    }

    /**
     * 方法描述: (获取验证码)
     *
     * @author : maoshuqi
     * @date : 2019-10-27 15:32
     * @version: V_1.0.0
     */
    @RequestMapping("image")
    public void image(HttpSession session, HttpServletResponse response) throws IOException {

        CreateValidateCode createValidateCode = CreateValidateCode.Instance();
        String validateCodeString = createValidateCode.getString();
        session.setAttribute("code",validateCodeString);
        response.setContentType("image/png");
        BufferedImage image = createValidateCode.getImage();
        ImageIO.write(image,"JPEG",response.getOutputStream());

    }

    /**
     * 方法描述: (退出方法)
     *
     * @return java.lang.String
     * @author : maoshuqi
     * @date : 2019-10-27 15:32
     * @version: V_1.0.0
     */
    @RequestMapping("exit")
    public String exit(HttpSession session) {
        session.invalidate();
        return "redirect:/login/login.jsp";
    }


}
