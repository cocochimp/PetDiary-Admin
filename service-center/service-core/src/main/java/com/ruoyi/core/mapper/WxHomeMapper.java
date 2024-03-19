package com.ruoyi.core.mapper;

import com.ruoyi.core.constant.ContentProvider;
import com.ruoyi.core.constant.MapperConstant;
import com.ruoyi.core.domain.UserContent;
import com.ruoyi.core.domain.vo.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 用户列表Mapper接口
 * 
 * @author cocochimp
 * @date 2023-12-04
 */
public interface WxHomeMapper
{
    /*查询所有contentInfo信息*/
    @SelectProvider(type= ContentProvider.class,method="showAllContent")
    @Results({
            @Result(column = "content_id", property = "contentId"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "content_type", property = "contentType"),
            @Result(column = "cover_path", property = "coverPath"),
            @Result(column = "update_time", property = "updateTime")
    })
    List<ContentInfo> showAllContent(@Param("contentType") Integer contentType, @Param("conditional") String conditional);

    /*查询所有contentInfo信息（热门）*/
    @Select("select uc.content_id content_id,count(ucl.content_id) like_count ,title,uc.user_id,content_type,cover_path,uc.update_time " +
            "from user_content uc " +
            "left join user_content_like ucl on uc.content_id = ucl.content_id " +
            "group by uc.content_id ")
    @Results({
            @Result(column = "content_id", property = "contentId"),
            @Result(column = "like_count", property = "likeCount")
    })
    List<ContentInfo> showHotContent();

    /*查询contentInfo详细信息（通过contentId）*/
    @Select("select content_id,title,description,user_id,content_type,cover_path,video_path,update_time " +
            "from user_content " +
            "where content_id=#{contentId} and " + MapperConstant.status)
    @Results({
            @Result(column = "content_id", property = "contentId"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "content_type", property = "contentType"),
            @Result(column = "cover_path", property = "coverPath"),
            @Result(column = "video_path", property = "videoPath"),
            @Result(column = "update_time", property = "updateTime")
    })
    ContentDetailInfo showAllContentById(@Param("contentId") String contentId);

    /*内容展示*/
    @Select("select pet_id " +
            "from user_content " +
            "where content_id=#{contentId} and " + MapperConstant.status)
    int showPetIdByContentId(@Param("contentId") Long content_id);

    /*用户信息*/
    @Select("select * " +
            "from user_info " +
            "where openid=#{openid}")
    ContentUserInfo showUserInfo(@Param("openid") String openid);

    /*点赞数（作品）*/
    @Select("select count(*) " +
            "from user_content_like " +
            "where content_id=#{content_id} and " + MapperConstant.del_flag)
    Integer contentLikeCount(@Param("content_id") Long content_id);

    /*点赞数（个人）*/
    @Select("select count(*) " +
            "from user_content_like " +
            "where user_id=#{userId} and " + MapperConstant.del_flag)
    Integer contentLikeCountByUserId(@Param("userId") String userId);

    /*评论信息*/
    @Select("select * " +
            "from user_content_comment " +
            "where content_id=#{content_id} and " + MapperConstant.del_flag)
    @Results({
            @Result(column = "comment_id", property = "commentId"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "content_id", property = "contentId"),
            @Result(column = "comment_info", property = "commentInfo"),
            @Result(column = "parent_comment_id", property = "parentCommentId")
    })
    List<ContentCommentInfo> contentCommentInfo(@Param("content_id") Long content_id);


    /*粉丝信息*/
    @Select("select distinct attention_user_id " +
            "from user_attention " +
            "where user_id=#{userId} and " + MapperConstant.del_flag)
    @Results({
            @Result(column = "attention_user_id", property = "attentionUserId")
    })
    List<String> contentFansInfo(@Param("userId") String userId);


    /*关注信息*/
    @Select("select distinct user_id " +
            "from user_attention " +
            "where attention_user_id=#{attention_user_id} and " + MapperConstant.del_flag)
    @Results({
            @Result(column = "user_id", property = "userId")
    })
    List<String> contentAttentionInfo(@Param("attention_user_id") String userId);

    @Select("select * " +
            "from user_pet " +
            "where pet_id=#{petId}")
    @Results({
            @Result(column = "pet_id", property = "petId")
    })
    WxPetListInfo showPetDetailByPetId(@Param("petId") int petId);

    @Select("select `pet_id`,`name` " +
            "from user_pet " +
            "where `type`=#{type}")
    @Results({
            @Result(column = "pet_id", property = "petId")
    })
    List<WxPetListInfo> showPetNameByPetType(@Param("type") int type);

    @Insert("insert into `user_content` (`title`, `description`, `user_id`, `content_type`, `cover_path`, `video_path`, `pet_id`, `status`, `reject_info`) " +
            "values " +
            "(#{title}, #{description}, #{userId}, #{contentType}, #{coverPath}, #{videoPath}, #{petId}, #{status} , #{rejectInfo});")
    void insertContentInfo(UserContent userContent);

    @Select("select *  " +
            "from user_info " +
            "where openid=#{openid} and " + MapperConstant.status + "and" + MapperConstant.del_flag)
    ContentUserInfo showUserInfoByUserId(@Param("openid") String openid);

    @Select("select up.pet_id petId, name,img,type,sum\n" +
            "from user_pet up\n" +
            "inner join (\n" +
            "    select uc.pet_id, COUNT(uc.pet_id) sum\n" +
            "    from user_content uc\n" +
            "\t\twhere uc.status=0\n" +
            "    group by uc.pet_id\n" +
            ") uc\n" +
            "on up.pet_id = uc.pet_id\n" +
            "order by uc.sum desc ")
    List<HotPetListInfo> showHotList();
}
