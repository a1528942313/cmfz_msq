package com.baizhi.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.UUID;

/**
 * 接口描述信息 (加密)
 *
 * @author : maoshuqi
 * @date : 2019-10-27 15:32
 * @version: V_1.0.0
 */
public class Md5UUIDSaltUtil {
    public static String uuid = UUID.randomUUID().toString().replace("-", "");
    //创建md5对象

    public static String createMd5Code(String code){return DigestUtils.md5Hex(code);}

    //进行密码校验

    public static boolean checkPassword(String userCode,String dbCode){
        if(dbCode.equals(createMd5Code(userCode))){return true;}
        else {return false;}
    }

    public static String getUUID(){
        return uuid;
    }

    public static String getSalt(){
        String salt = uuid.substring(0, 5);
        return salt;
    }

 /*   public static void main(String[] args) {
        String md5Code = createMd5Code("123123"+"asd");
        //6e0f535a5544717591d615d3a264d43e
        boolean b = checkPassword("123123asd", "6e0f535a5544717591d615d3a264d43e");
        System.out.println(b);
    }*/

}
