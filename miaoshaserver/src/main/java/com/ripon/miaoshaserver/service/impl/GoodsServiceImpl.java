package com.ripon.miaoshaserver.service.impl;

import com.ripon.miaoshaserver.dao.MiaoShaGoodsMapper;
import com.ripon.miaoshaserver.domain.MiaoShaGoods;
import com.ripon.miaoshaserver.domain.MiaoShaGoodsExample;
import com.ripon.miaoshaserver.dto.GoodsDTO;
import com.ripon.miaoshaserver.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    MiaoShaGoodsMapper miaoShaGoodsMapper;

    @Override
    public GoodsDTO getGoodsDTOById(Long id) {
        return miaoShaGoodsMapper.selectGoodsDTOByPrimaryKey(id);
    }

    @Override
    public int reduceMiaoshaStock(Long goodsId) {
         return miaoShaGoodsMapper.reduceStock(goodsId);
    }

    @Override
    public void updateMiaoshaStock(Long goodsId, Integer stock) {
        MiaoShaGoods goods = new MiaoShaGoods();
        goods.setMiaoshaStock(stock);
        MiaoShaGoodsExample example = new MiaoShaGoodsExample();
        MiaoShaGoodsExample.Criteria criteria = example.createCriteria();
        criteria.andGoodsIdEqualTo(goodsId);
        miaoShaGoodsMapper.updateByExampleSelective(goods, example);
    }

    @Override
    public List<GoodsDTO> listMiaoshaGoods() {
        return miaoShaGoodsMapper.selectAllMiaoshaGoods();
    }
}
