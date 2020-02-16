package com.ripon.miaoshaserver.vo;


import com.ripon.miaoshaserver.annotation.IsMobile;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class RegisterVO {
    @NotNull(message = "手机号不能为空")
    @IsMobile
    private String mobile;
    private String nickname;
    @NotNull(message = "密码不能为空")
    @Length(min = 6, message = "密码长度不能少于6位")
    private String password;

    public RegisterVO() {
    }

    public RegisterVO(String mobile, String nickname, String password) {
        this.mobile = mobile;
        this.nickname = nickname;
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
