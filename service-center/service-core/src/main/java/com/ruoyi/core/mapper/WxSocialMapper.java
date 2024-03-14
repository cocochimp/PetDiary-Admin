package com.ruoyi.core.mapper;

import com.ruoyi.core.domain.UserPet;
import com.ruoyi.core.domain.vo.WxNationInfo;
import com.ruoyi.core.domain.vo.WxPetListInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用户列表Mapper接口
 * 
 * @author cocochimp
 * @date 2023-12-04
 */
public interface WxSocialMapper
{
    @Select("select \n" +
            "    case when nation_top5.nation is not null then nation_top5.nation\n" +
            "         else '其他'\n" +
            "    end as nationName,\n" +
            "    count(*) as number\n" +
            "from (\n" +
            "    select nation, count(nation) as nation_count\n" +
            "    from user_pet\n" +
            "\t\twhere type = #{type}\n" +
            "    group by nation\n" +
            "    order by nation_count desc\n" +
            "    limit 5\n" +
            ") as nation_top5\n" +
            "right join user_pet on user_pet.nation = nation_top5.nation\n" +
            "where user_pet.type = #{type}\n" +
            "group by nationName\n" +
            "order by case when nationName = '其他' then 2 else 1 end, number desc;")
    List<WxNationInfo> showNationNumByType(@Param("type") int type);

    @Select("select `pet_id`,`name`,`img`,`character` " +
            "from user_pet " +
            "where type=#{type} and nation like concat('%',#{nation},'%')")
    @Results({
            @Result(column = "pet_id", property = "petId")
    })
    List<WxPetListInfo> showListByNation(@Param("type") int type, @Param("nation") String nation);

    @Select("select * " +
            "from user_pet " +
            "where pet_id=#{petId}")
    @Results({
            @Result(column = "pet_id", property = "petId")
    })
    List<UserPet> showPetDetailByPetId(@Param("petId") int petId);

}
