package com.ripon.miaoshaserver.dao;

import com.ripon.miaoshaserver.domain.MiaoShaGoods;
import com.ripon.miaoshaserver.domain.MiaoShaGoodsExample;
import java.util.List;

import com.ripon.miaoshaserver.dto.GoodsDTO;
import org.apache.ibatis.annotations.Param;

public interface MiaoShaGoodsMapper {
    long countByExample(MiaoShaGoodsExample example);

    int deleteByExample(MiaoShaGoodsExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MiaoShaGoods record);

    int insertSelective(MiaoShaGoods record);

    List<MiaoShaGoods> selectByExample(MiaoShaGoodsExample example);

    MiaoShaGoods selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MiaoShaGoods record, @Param("example") MiaoShaGoodsExample example);

    int updateByExample(@Param("record") MiaoShaGoods record, @Param("example") MiaoShaGoodsExample example);

    int updateByPrimaryKeySelective(MiaoShaGoods record);

    int updateByPrimaryKey(MiaoShaGoods record);

    GoodsDTO selectGoodsDTOByPrimaryKey(Long id);

    List<GoodsDTO> selectAllMiaoshaGoods();

    int reduceStock(Long id);
}