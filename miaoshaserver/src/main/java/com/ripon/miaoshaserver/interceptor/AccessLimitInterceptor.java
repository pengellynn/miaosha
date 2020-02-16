package com.ripon.miaoshaserver.interceptor;

import com.ripon.miaoshaserver.annotation.AccessLimit;
import com.ripon.miaoshaserver.redis.RedisService;
import com.ripon.miaoshaserver.util.IpAddressUtils;
import com.ripon.miaoshaserver.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AccessLimitInterceptor implements HandlerInterceptor {
    @Autowired
    RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        AccessLimit accessLimit = handlerMethod.getMethodAnnotation(AccessLimit.class);
        if (accessLimit == null) {
            return true;
        }
        int seconds = accessLimit.seconds();
        int maxCount = accessLimit.maxCount();
        boolean needLogin = accessLimit.needLogin();
        String keySuffix= "";
        if (needLogin) {
            String authorization = request.getHeader("Authorization");
            if (authorization == null || !authorization.startsWith("Bearer ")) {
                throw new RuntimeException("无token，请重新登录");
            }
            String token = authorization.substring(7);
            String userId = JwtUtils.verifyToken(token);
            request.setAttribute("userId", userId);
            keySuffix = userId;
        } else{
            keySuffix = IpAddressUtils.getIpAddress(request);
        }
        Integer count  = redisService.getAccessCount(keySuffix);
        if (count == null) {
            redisService.setAccessCount(keySuffix, 1, seconds);
        } else if (count < maxCount) {
            redisService.increaseAccessCount(keySuffix);
        } else {
            return false;
        }
        return true;

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
