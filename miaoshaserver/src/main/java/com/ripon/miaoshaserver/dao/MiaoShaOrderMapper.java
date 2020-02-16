package com.ripon.miaoshaserver.dao;

import com.ripon.miaoshaserver.domain.MiaoShaOrder;
import com.ripon.miaoshaserver.domain.MiaoShaOrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MiaoShaOrderMapper {
    long countByExample(MiaoShaOrderExample example);

    int deleteByExample(MiaoShaOrderExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MiaoShaOrder record);

    int insertSelective(MiaoShaOrder record);

    List<MiaoShaOrder> selectByExample(MiaoShaOrderExample example);

    MiaoShaOrder selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MiaoShaOrder record, @Param("example") MiaoShaOrderExample example);

    int updateByExample(@Param("record") MiaoShaOrder record, @Param("example") MiaoShaOrderExample example);

    int updateByPrimaryKeySelective(MiaoShaOrder record);

    int updateByPrimaryKey(MiaoShaOrder record);

    List<MiaoShaOrder> selectByGoodsIdAndUserId(@Param("goodsId") Long goodsId, @Param("userId") Long userId);
}