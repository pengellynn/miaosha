package com.ripon.miaoshaserver.domain;

import java.math.BigDecimal;
import java.util.Date;

public class MiaoShaGoods {
    private Long id;

    private Long goodsId;

    private Integer miaoshaStock;

    private Date startTime;

    private Date endTime;

    private BigDecimal miaoshaPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getMiaoshaStock() {
        return miaoshaStock;
    }

    public void setMiaoshaStock(Integer miaoshaStock) {
        this.miaoshaStock = miaoshaStock;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public BigDecimal getMiaoshaPrice() {
        return miaoshaPrice;
    }

    public void setMiaoshaPrice(BigDecimal miaoshaPrice) {
        this.miaoshaPrice = miaoshaPrice;
    }

}