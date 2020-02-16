package com.ripon.miaoshaserver.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.ripon.miaoshaserver.domain.MiaoShaOrder;
import com.ripon.miaoshaserver.domain.MiaoShaUser;
import com.ripon.miaoshaserver.dto.GoodsDTO;
import com.ripon.miaoshaserver.service.GoodsService;
import com.ripon.miaoshaserver.service.MiaoShaService;
import com.ripon.miaoshaserver.service.OrderService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MQReceiver {
    @Autowired
    MiaoShaService miaoShaService;
    @Autowired
    GoodsService goodsService;
    @Autowired
    OrderService orderService;

    @RabbitListener(queues = MQConfig.MIAOSHA_QUEUE)
    public void receiver(String message) {
        MiaoshaMessage mm = JSON.toJavaObject(JSON.parseObject(message),MiaoshaMessage.class);
        MiaoShaUser user = mm.getUser();
        Long goodsId = mm.getGoodsId();
        GoodsDTO goods = goodsService.getGoodsDTOById(goodsId);
        Integer stock = goods.getMiaoshaStock();
        if (stock <= 0) {
            return;
        }
        List<MiaoShaOrder> list = orderService.getByGoodsIdAndUserId(goodsId, user.getId());
        if (list.size() != 0) {
            return;
        }
        miaoShaService.miaosha(user, goods);
    }
}
