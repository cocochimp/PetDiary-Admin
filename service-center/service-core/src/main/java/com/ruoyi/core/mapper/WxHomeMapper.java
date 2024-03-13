package com.ruoyi.core.mapper;

import com.ruoyi.core.constant.MapperConstant;
import com.ruoyi.core.domain.UserAttention;
import com.ruoyi.core.domain.UserComment;
import com.ruoyi.core.domain.UserInfo;
import com.ruoyi.core.domain.vo.ContentInfo;
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
public interface WxHomeMapper
{
    /*内容展示*/
    @Select("select content_id,title,description,user_id,content_type,cover_path,video_path,update_time from user_content where status=2")
    @Results({
            @Result(column = "content_id", property = "contentId"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "content_type", property = "contentType"),
            @Result(column = "cover_path", property = "coverPath"),
            @Result(column = "video_path", property = "videoPath"),
            @Result(column = "update_time", property = "updateTime")
    })
    List<ContentInfo> showAllContent();

    /*用户信息*/
    @Select("select * from user_info where openid=#{openid}")
    UserInfo showUserInfo(@Param("openid") String openid);


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
            @Result(column = "parent_comment_id", property = "parentCommentId"),
            @Result(column = "status", property = "status"),
            @Result(column = "reject_info", property = "rejectInfo"),
            @Result(column = "del_flag", property = "delFlag")
    })
    List<UserComment> contentCommentInfo(@Param("content_id") Long content_id);


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
}
