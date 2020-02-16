package com.ripon.miaoshaserver.service;

import com.ripon.miaoshaserver.domain.MiaoShaUser;
import com.ripon.miaoshaserver.domain.Order;
import com.ripon.miaoshaserver.dto.GoodsDTO;

public interface MiaoShaService {
    void miaosha(MiaoShaUser user, GoodsDTO goodsDTO);
    long getMiaoshaResult(Long goodsId, Long userId);
}
