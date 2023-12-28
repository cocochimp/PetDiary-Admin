package com.ruoyi.core.mapper;

import com.ruoyi.core.domain.UserBlock;
import com.ruoyi.core.domain.vo.UserBlockExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserBlockMapper {
    long countByExample(UserBlockExample example);

    int deleteByExample(UserBlockExample example);

    int deleteByPrimaryKey(Long blockId);

    int insert(UserBlock record);

    int insertSelective(UserBlock record);

    List<UserBlock> selectByExample(UserBlockExample example);

    UserBlock selectByPrimaryKey(Long blockId);

    int updateByExampleSelective(@Param("record") UserBlock record, @Param("example") UserBlockExample example);

    int updateByExample(@Param("record") UserBlock record, @Param("example") UserBlockExample example);

    int updateByPrimaryKeySelective(UserBlock record);

    int updateByPrimaryKey(UserBlock record);
}