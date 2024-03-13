package com.ruoyi.core.mapper;

import com.ruoyi.core.domain.UserInfo;
import com.ruoyi.core.domain.UserPet;
import com.ruoyi.core.domain.vo.ContentInfo;
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
    /*内容展示*/
    @Select("select content_id,title,description,user_id,content_type,cover_path,video_path,update_time from user_content where status=2")
    @Results({
            @Result(column = "content_id", property = "contentId"),
            @Result(column = "user_id", property = "userId")
    })
    List<ContentInfo> showAllContent();

    /*用户信息*/
    @Select("select * from user_info where openid=#{openid}")
    UserInfo showUserInfo(@Param("openid") String openid);


    @Select("select  " +
            "    case when nation_top5.nation is not null then nation_top5.nation " +
            "         else '其他' " +
            "    end as nationName, " +
            "    count(*) as number " +
            "from ( " +
            "    select nation, count(nation) as nation_count " +
            "    from user_pet " +
            "    where type = "+ "#{type}" +
            "    group by nation " +
            "    order by nation_count desc " +
            "    limit 5 " +
            ") as nation_top5 " +
            "right join user_pet on user_pet.nation = nation_top5.nation " +
            "where user_pet.type = " + "#{type}" +
            "group by nationName " +
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
