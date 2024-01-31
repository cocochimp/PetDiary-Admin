package com.ruoyi.core.mapper;

import com.ruoyi.core.domain.UserComment;
import com.ruoyi.core.domain.UserContent;
import com.ruoyi.core.domain.UserInfo;
import com.ruoyi.core.domain.vo.ContentInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 图文管理Mapper接口
 * 
 * @author cocochimp
 * @date 2023-12-04
 */
public interface UserContentMapper
{
    /**
     * 查询图文管理
     * 
     * @param contentId 图文管理主键
     * @return 图文管理
     */
    public UserContent selectUserPictureByContentId(Long contentId);

    /**
     * 查询图文管理列表
     * 
     * @param userContent 图文管理
     * @return 图文管理集合
     */
    public List<UserContent> selectUserPictureList(UserContent userContent);

    /**
     * 新增图文管理
     * 
     * @param userContent 图文管理
     * @return 结果
     */
    public int insertUserPicture(UserContent userContent);

    /**
     * 修改图文管理
     * 
     * @param userContent 图文管理
     * @return 结果
     */
    public int updateUserPicture(UserContent userContent);

    /**
     * 删除图文管理
     * 
     * @param contentId 图文管理主键
     * @return 结果
     */
    public int deleteUserPictureByContentId(Long contentId);

    /**
     * 批量删除图文管理
     * 
     * @param contentIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteUserPictureByContentIds(Long[] contentIds);

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

    String del_flag=" and del_flag=0 ";

    /*点赞数*/
    @Select("select count(*) from user_content_like where content_id=#{content_id}" + del_flag)
    Integer contentLikeCount(@Param("content_id") Long content_id);

    /*评论信息*/
    @Select("select * from user_content_comment where content_id=#{content_id}" + del_flag)
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
}