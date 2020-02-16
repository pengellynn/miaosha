package com.ripon.miaoshaserver.dao;

import com.ripon.miaoshaserver.domain.MiaoShaUser;
import com.ripon.miaoshaserver.domain.MiaoShaUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MiaoShaUserMapper {
    long countByExample(MiaoShaUserExample example);

    int deleteByExample(MiaoShaUserExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MiaoShaUser record);

    int insertSelective(MiaoShaUser record);

    List<MiaoShaUser> selectByExample(MiaoShaUserExample example);

    MiaoShaUser selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MiaoShaUser record, @Param("example") MiaoShaUserExample example);

    int updateByExample(@Param("record") MiaoShaUser record, @Param("example") MiaoShaUserExample example);

    int updateByPrimaryKeySelective(MiaoShaUser record);

    int updateByPrimaryKey(MiaoShaUser record);
}