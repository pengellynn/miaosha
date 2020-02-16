package com.ripon.miaoshaserver.service;

import com.ripon.miaoshaserver.domain.MiaoShaGoods;
import com.ripon.miaoshaserver.dto.GoodsDTO;

import java.util.List;

public interface GoodsService {
    GoodsDTO getGoodsDTOById(Long id);
    int reduceMiaoshaStock(Long goodsId);
    void updateMiaoshaStock(Long goodsId, Integer stock);
    List<GoodsDTO> listMiaoshaGoods();
}
