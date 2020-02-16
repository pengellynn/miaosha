package com.ripon.miaoshaserver.util;

import org.springframework.util.DigestUtils;

public class Md5Utils {
    public static String addSalt(String formPassword, String salt) {
        String str = formPassword + salt.charAt(0) + salt.charAt(2) + salt.charAt(4) + salt.charAt(5);
        return DigestUtils.md5DigestAsHex(str.getBytes());
    }

    public static String inputPassToFormPassword(String inputPassword) {
        String str = inputPassword + "cvbijk";
        return DigestUtils.md5DigestAsHex(str.getBytes());
    }
}
