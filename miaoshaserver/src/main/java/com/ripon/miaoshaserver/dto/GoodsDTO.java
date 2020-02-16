package com.ripon.miaoshaserver.dto;

import com.ripon.miaoshaserver.domain.Goods;

import java.math.BigDecimal;
import java.util.Date;

public class GoodsDTO extends Goods {
    private Integer miaoshaStock;

    private Date startTime;

    private Date endTime;

    private BigDecimal miaoshaPrice;

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

    @Override
    public String toString() {
        return "GoodsDTO{" +
                "miaoshaStock=" + miaoshaStock +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", miaoshaPrice=" + miaoshaPrice +
                '}';
    }
}
