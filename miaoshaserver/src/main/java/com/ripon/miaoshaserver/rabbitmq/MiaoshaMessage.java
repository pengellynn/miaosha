package com.ripon.miaoshaserver.rabbitmq;

import com.ripon.miaoshaserver.domain.MiaoShaUser;

import java.io.Serializable;

public class MiaoshaMessage {
    private MiaoShaUser user;
    private Long goodsId;

    public MiaoshaMessage() {
    }

    public MiaoshaMessage(MiaoShaUser user, Long goodsId) {
        this.user = user;
        this.goodsId = goodsId;
    }

    public MiaoShaUser getUser() {
        return user;
    }

    public void setUser(MiaoShaUser user) {
        this.user = user;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }
}
