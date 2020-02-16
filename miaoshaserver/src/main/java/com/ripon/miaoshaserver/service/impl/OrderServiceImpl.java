package com.ripon.miaoshaserver.service.impl;

import com.ripon.miaoshaserver.dao.MiaoShaOrderMapper;
import com.ripon.miaoshaserver.dao.OrderMapper;
import com.ripon.miaoshaserver.domain.*;
import com.ripon.miaoshaserver.dto.GoodsDTO;
import com.ripon.miaoshaserver.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    MiaoShaOrderMapper miaoShaOrderMapper;
    @Autowired
    OrderMapper orderMapper;
    @Override
    public List<MiaoShaOrder> getByGoodsIdAndUserId(Long goodsId, Long userId) {
        MiaoShaOrderExample example = new MiaoShaOrderExample();
        MiaoShaOrderExample.Criteria criteria = example.createCriteria();
        criteria.andGoodsIdEqualTo(goodsId);
        criteria.andUserIdEqualTo(userId);
        return miaoShaOrderMapper.selectByExample(example);
    }

    @Transactional
    @Override
    public void createOrder(MiaoShaUser user, GoodsDTO goods) {
        Order order = new Order();
        order.setUserId(user.getId());
        order.setGoodsId(goods.getId());
        order.setGoodsName(goods.getName());
        order.setGoodsPrice(goods.getMiaoshaPrice());
        order.setGoodsCount(1);
        order.setStatus(1);
        order.setCreateTime(new Date());
        order.setDeliveryAddrId(null);
        order.setOrderChannel(null);
        order.setPayTime(null);
        orderMapper.insertSelective(order);
        MiaoShaOrder miaoShaOrder = new MiaoShaOrder();
        miaoShaOrder.setGoodsId(goods.getId());
        miaoShaOrder.setOrderId(order.getId());
        miaoShaOrder.setUserId(user.getId());
        miaoShaOrderMapper.insertSelective(miaoShaOrder);
    }

    @Override
    public Order getOrderById(Long orderId) {
        return orderMapper.selectByPrimaryKey(orderId);
    }
}
