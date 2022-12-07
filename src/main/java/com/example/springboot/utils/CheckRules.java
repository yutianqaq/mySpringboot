package com.example.springboot.utils;

public class CheckRules {
    /**
     * 用户名验证
     *  @param name
     *  @return
     */
    public static boolean checkName(String name) {
//      String regExp = "^[^0-9][\\w_]{5,9}$";
        String regExp = "^[A-Za-z0-9]{3,20}$";
        if(name.matches(regExp)) {
            return true;
        }else {
            return false;
        }
    }
    /**
     * 密码验证
     *  @param pwd
     *  @return
     */
    public static boolean checkPwd(String pwd) {
//      String regExp = "^[\\w_]{6,20}$";
        String regExp = "^[A-za-z0-9]{6,20}$";
        if(pwd.matches(regExp)) {
            return true;
        }
        return false;
    }
}
