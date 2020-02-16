package com.ripon.miaoshaserver.service;

import com.ripon.miaoshaserver.domain.MiaoShaOrder;
import com.ripon.miaoshaserver.domain.MiaoShaUser;
import com.ripon.miaoshaserver.domain.Order;
import com.ripon.miaoshaserver.dto.GoodsDTO;

import java.util.List;

public interface OrderService {
    List<MiaoShaOrder> getByGoodsIdAndUserId(Long goodsId, Long userId);

    void createOrder(MiaoShaUser user, GoodsDTO goods);

    Order getOrderById(Long orderId);
}
