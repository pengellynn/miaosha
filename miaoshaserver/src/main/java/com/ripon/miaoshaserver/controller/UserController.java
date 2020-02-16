package com.ripon.miaoshaserver.controller;

import com.ripon.miaoshaserver.vo.LoginVO;
import com.ripon.miaoshaserver.vo.RegisterVO;
import com.ripon.miaoshaserver.annotation.VerifyToken;
import com.ripon.miaoshaserver.domain.BaseUser;
import com.ripon.miaoshaserver.result.Result;
import com.ripon.miaoshaserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/user/register")
    public Result register(@RequestBody @Validated RegisterVO registerVO) {
        userService.register(registerVO);
        return Result.success(true);

    }

    @PostMapping("/user/login")
    public Result login(@RequestBody @Validated LoginVO loginVO) {
        Map<String, Object> map = userService.login(loginVO);
        return Result.success(map);
    }

    //  从 redis缓存中查 650/sec
    // 直接从mysql中查 530/sec
    @VerifyToken()
    @GetMapping("/user/info")
    public Result getUserInfo(HttpServletRequest request) {
        Long userId = Long.parseLong(request.getAttribute("userId").toString());
        BaseUser user = userService.getUserInfo(userId);
        return Result.success(user);
    }

}
