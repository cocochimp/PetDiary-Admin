package com.ruoyi.core.mapper;

import com.ruoyi.core.constant.MapperConstant;
import com.ruoyi.core.domain.UserAttention;
import com.ruoyi.core.domain.UserContent;
import com.ruoyi.core.domain.vo.ContentCommentInfo;
import com.ruoyi.core.domain.vo.ContentInfo;
import com.ruoyi.core.domain.vo.ContentUserInfo;
import com.ruoyi.core.domain.vo.WxPetListInfo;
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
    /*内容展示*/
    @Select("select content_id,title,description,user_id,content_type,cover_path,video_path,update_time from user_content where "+ MapperConstant.status)
    @Results({
            @Result(column = "content_id", property = "contentId"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "content_type", property = "contentType"),
            @Result(column = "cover_path", property = "coverPath"),
            @Result(column = "video_path", property = "videoPath"),
            @Result(column = "update_time", property = "updateTime")
    })
    List<ContentInfo> showAllContent();

    /*内容展示*/
    @Select("select content_id,title,description,user_id,content_type,cover_path,video_path,update_time from user_content where "+ MapperConstant.status + " and content_id=#{contentId} ")
    @Results({
            @Result(column = "content_id", property = "contentId"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "content_type", property = "contentType"),
            @Result(column = "cover_path", property = "coverPath"),
            @Result(column = "video_path", property = "videoPath"),
            @Result(column = "update_time", property = "updateTime")
    })
    List<ContentInfo> showAllContentById(@Param("contentId") String contentId);

    /*内容展示*/
    @Select("select pet_id from user_content where content_id=#{contentId} and " + MapperConstant.status)
    int showPetIdByContentId(@Param("contentId") Long content_id);

    /*用户信息*/
    @Select("select * from user_info where openid=#{openid}")
    ContentUserInfo showUserInfo(@Param("openid") String openid);


    /*点赞数*/
    @Select("select count(*) from user_content_like where content_id=#{content_id}" + MapperConstant.del_flag)
    Integer contentLikeCount(@Param("content_id") Long content_id);

    /*评论信息*/
    @Select("select * from user_content_comment where content_id=#{content_id}" + MapperConstant.del_flag)
    @Results({
            @Result(column = "comment_id", property = "commentId"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "content_id", property = "contentId"),
            @Result(column = "comment_info", property = "commentInfo"),
            @Result(column = "parent_comment_id", property = "parentCommentId")
    })
    List<ContentCommentInfo> contentCommentInfo(@Param("content_id") Long content_id);


    /*粉丝信息*/
    @Select("select attention_user_id from user_attention where user_id=#{userId} " + MapperConstant.del_flag)
    @Results({
            @Result(column = "attention_user_id", property = "attentionUserId")
    })
    List<UserAttention> contentFansInfo(@Param("userId") String userId);


    /*关注信息*/
    @Select("select user_id from user_attention where attention_user_id=#{attention_user_id} " + MapperConstant.del_flag)
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
}
