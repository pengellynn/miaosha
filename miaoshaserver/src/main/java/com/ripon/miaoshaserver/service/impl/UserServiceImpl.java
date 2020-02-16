package com.ripon.miaoshaserver.service.impl;

import com.ripon.miaoshaserver.result.CodeMessageEnum;
import com.ripon.miaoshaserver.vo.LoginVO;
import com.ripon.miaoshaserver.vo.RegisterVO;
import com.ripon.miaoshaserver.dao.MiaoShaUserMapper;
import com.ripon.miaoshaserver.domain.BaseUser;
import com.ripon.miaoshaserver.domain.MiaoShaUser;
import com.ripon.miaoshaserver.exception.GlobalException;
import com.ripon.miaoshaserver.redis.RedisService;
import com.ripon.miaoshaserver.service.UserService;
import com.ripon.miaoshaserver.util.JwtUtils;
import com.ripon.miaoshaserver.util.Md5Utils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    RedisService redisService;

    @Autowired
    MiaoShaUserMapper miaoShaUserMapper;

    @Override
    public void register(RegisterVO registerVO) {
        Long id = Long.parseLong(registerVO.getMobile());
        String nickname = registerVO.getNickname();
        String formPassword = registerVO.getPassword();
        String dbSalt = RandomStringUtils.random(6,true,true);
        String dbPassword = Md5Utils.addSalt(formPassword, dbSalt);
        Date registerDate = new Date();
        MiaoShaUser user = new MiaoShaUser();
        user.setId(id);
        user.setNickname(nickname);
        user.setPassword(dbPassword);
        user.setSalt(dbSalt);
        user.setRegisterTime(registerDate);
//        user.setLastLoginTime(registerDate);
        user.setLoginCount(0);
        miaoShaUserMapper.insertSelective(user);
    }

    @Override
    public Map<String, Object> login(LoginVO loginVO) {
        String mobile = loginVO.getMobile();
        Long id = Long.parseLong(mobile);
        String formPassword = loginVO.getPassword();
        MiaoShaUser user = getUserById(id);
        if(user == null) {
            throw new GlobalException(CodeMessageEnum.MOBLIE_NOT_EXIST);
        }
        String dbPassword = user.getPassword();
        String dbSalt = user.getSalt();
        String calculatePassword = Md5Utils.addSalt(formPassword, dbSalt);
        if (!calculatePassword.equals(dbPassword)) {
            throw new GlobalException(CodeMessageEnum.PASSWORD_ERROR);
        }
        String token = JwtUtils.createToken(mobile);
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("id", user.getId());
        map.put("nickname", user.getNickname());
        redisService.setUser(id, user);
        return map;
    }

    @Override
    public BaseUser getUserInfo(Long id) {
        MiaoShaUser user = getUserById(id);
        BaseUser baseUser = new BaseUser();
        baseUser.setId(user.getId());
        baseUser.setNickname(user.getNickname());
        return baseUser;
    }

    @Override
    public MiaoShaUser getUserById(Long id) {
        MiaoShaUser user = null;
        if (redisService.checkUser(id)) {
            user = redisService.getUser(id);
        } else {
            user = miaoShaUserMapper.selectByPrimaryKey(id);
            redisService.setUser(id, user);
        }
        return user;
    }
}
