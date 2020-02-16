package com.ripon.miaoshaserver.redis;

import com.alibaba.fastjson.JSONPObject;
import com.ripon.miaoshaserver.domain.MiaoShaUser;
import com.ripon.miaoshaserver.dto.GoodsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    private static Logger logger = LoggerFactory.getLogger(RedisService.class);

    @Autowired
    RedisTemplate redisTemplate;

    private static final String USER_KEY = "user:info:";
    private static final String SECKILL_GOODS_STOCK_KEY = "seckill:goods:stock";
    private static final String SECKILL_OVER = "seckill:over";
    private static final String SECKILL_PATH = "seckill:path:";
    private static final String SECKILL_RECORD = "seckill:record:";
    private static final String ACCESS_COUNT = "access:count:";
    private static final String VERIFY_CODE = "seckill:verifyCode";

    public void setUser(Long id, MiaoShaUser user) {
//        Calendar calendar = Calendar.getInstance();
//        String key = USER_KEY+ calendar.get(Calendar.DATE) + calendar.get(Calendar.HOUR);
//        redisTemplate.opsForHash().put(key, id, user);
//        redisTemplate.expire(key, 24, TimeUnit.HOURS);
        String key = USER_KEY + id;
        redisTemplate.opsForValue().set(key, user);
        redisTemplate.expire(key, 60 * 60 * 24, TimeUnit.SECONDS);
    }

    public boolean checkUser(Long id) {
        return redisTemplate.hasKey(USER_KEY + id);
    }

    public MiaoShaUser getUser(Long id) {
        return (MiaoShaUser) redisTemplate.opsForValue().get(USER_KEY + id);
    }

    public void setGoodsStock(String id, Integer stock) {
        redisTemplate.opsForHash().put(SECKILL_GOODS_STOCK_KEY, id, stock);
    }

    public Long reduceStock(String id) {
        return redisTemplate.opsForHash().increment(SECKILL_GOODS_STOCK_KEY, id, -1);
    }

    public void setSeckillOver(Long id, boolean value) {
        redisTemplate.opsForSet().add(id);
    }

    public Boolean checkSeckillOver(Long id) {
        return redisTemplate.opsForSet().isMember(SECKILL_OVER, id);
    }

    public void setSeckillPath(Long userId, Long goodsId, String path) {
        String key = SECKILL_PATH+ userId + ":" + goodsId;
        redisTemplate.opsForValue().set(key, path);
        redisTemplate.expire(key, 60, TimeUnit.SECONDS);
    }

    public String getSeckillPath(Long userId, Long goodsId) {
        String key = SECKILL_PATH + userId + ":" + goodsId;
        return (String) redisTemplate.opsForValue().get(key);
    }

    public boolean checkSeckillRecord(Long goodsId, Long userId) {
        String key = SECKILL_RECORD + goodsId;
        return redisTemplate.opsForSet().isMember(key, userId);
    }

    public void setSeckillRecord(Long goodsId, Long userId) {
        String key = SECKILL_RECORD + goodsId;
        redisTemplate.opsForSet().add(key, userId);
    }

    public Integer getAccessCount (String suffix) {
        String key = ACCESS_COUNT + suffix;
        return (Integer)redisTemplate.opsForValue().get(key);
    }

    public void setAccessCount (String suffix, Integer count, Integer seconds) {
        String key = ACCESS_COUNT + suffix;
        redisTemplate.opsForValue().set(key, count);
        redisTemplate.expire(key,seconds,TimeUnit.SECONDS);
    }
    public void increaseAccessCount (String suffix) {
        String key =ACCESS_COUNT + suffix;
        redisTemplate.opsForValue().increment(key);
    }

    public void setVerifyCode(Long userId, Long goodsId, String verifyCode) {
        String key = VERIFY_CODE + userId +":" +goodsId;
        redisTemplate.opsForValue().set(key, verifyCode);
        redisTemplate.expire(key, 60 , TimeUnit.SECONDS);
    }

    public String getVerifyCode(Long userId, Long goodsId) {
        String key = VERIFY_CODE + userId + ":" + goodsId;
        return (String)redisTemplate.opsForValue().get(key);
    }
}
