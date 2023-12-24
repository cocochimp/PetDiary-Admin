package com.ruoyi.common.core.mapper;

import java.util.List;

import com.ruoyi.common.core.entity.UserBan;
import com.ruoyi.common.core.entity.UserBanExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserBanMapper {
    long countByExample(UserBanExample example);

    int deleteByExample(UserBanExample example);

    int deleteByPrimaryKey(Long banId);

    int insert(UserBan record);

    int insertSelective(UserBan record);

    List<UserBan> selectByExample(UserBanExample example);

    UserBan selectByPrimaryKey(Long banId);

    int updateByExampleSelective(@Param("record") UserBan record, @Param("example") UserBanExample example);

    int updateByExample(@Param("record") UserBan record, @Param("example") UserBanExample example);

    int updateByPrimaryKeySelective(UserBan record);

    int updateByPrimaryKey(UserBan record);
}
