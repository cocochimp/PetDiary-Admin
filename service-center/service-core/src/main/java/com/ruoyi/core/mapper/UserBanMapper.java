package com.ruoyi.core.mapper;

import com.ruoyi.core.domain.UserBan;
import com.ruoyi.core.domain.vo.UserBanExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
