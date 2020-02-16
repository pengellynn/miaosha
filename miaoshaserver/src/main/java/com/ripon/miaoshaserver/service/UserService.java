package com.ripon.miaoshaserver.service;

import com.ripon.miaoshaserver.vo.LoginVO;
import com.ripon.miaoshaserver.vo.RegisterVO;
import com.ripon.miaoshaserver.domain.BaseUser;
import com.ripon.miaoshaserver.domain.MiaoShaUser;

import java.util.Map;

public interface UserService {

    public void register(RegisterVO registerVo);
    public Map<String, Object> login(LoginVO loginVo);
    public BaseUser getUserInfo(Long id);
    public MiaoShaUser getUserById(Long id);
}
