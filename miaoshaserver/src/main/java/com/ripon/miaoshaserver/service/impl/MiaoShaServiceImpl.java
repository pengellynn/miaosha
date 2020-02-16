package com.ripon.miaoshaserver.service.impl;

import com.ripon.miaoshaserver.domain.MiaoShaOrder;
import com.ripon.miaoshaserver.domain.MiaoShaUser;
import com.ripon.miaoshaserver.dto.GoodsDTO;
import com.ripon.miaoshaserver.redis.RedisService;
import com.ripon.miaoshaserver.service.GoodsService;
import com.ripon.miaoshaserver.service.MiaoShaService;
import com.ripon.miaoshaserver.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MiaoShaServiceImpl implements MiaoShaService {
    @Autowired
    GoodsService goodsService;
    @Autowired
    OrderService orderService;
    @Autowired
    RedisService redisService;

    @Transactional
    @Override
    public void miaosha(MiaoShaUser user, GoodsDTO goodsDTO) {
        Long goodsId = goodsDTO.getId();
        int result = goodsService.reduceMiaoshaStock(goodsId);
        if ( result > 0) {
            orderService.createOrder(user,goodsDTO);
            // 缓存该用户秒杀记录
            redisService.setSeckillRecord(goodsId, user.getId());
        }else {
            redisService.setSeckillOver(goodsId, true);
        }
//        Order order = null;
//        if (result>0){
//            order=orderService.createOrder(user, goodsDTO);
//        }
//        return order;
    }

    public long getMiaoshaResult(Long goodsId, Long userId) {
        List<MiaoShaOrder> list = orderService.getByGoodsIdAndUserId(goodsId, userId);
        if (list.size() !=0) {
            return list.get(0).getOrderId();
        } else {
            boolean isOver = redisService.checkSeckillOver(goodsId);
            if (isOver) {
                return -1;
            }else{
                return 0;
            }
        }
    }

}
