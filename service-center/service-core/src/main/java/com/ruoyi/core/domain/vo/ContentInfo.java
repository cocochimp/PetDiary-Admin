package com.ruoyi.core.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * content对象
 *
 * @author cocochimp
 * @date 2024-01-31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContentInfo
{
    private static final long serialVersionUID = 1L;

    /** 内容ID */
    private Long contentId;

    /** 标题 */
    private String title;

    /** 内容类型（0图文 1视频） */
    private String contentType;

    /** 图片/封面地址（通过“，”分割） */
    private String coverPath;

    /** 内容发布人ID */
    private String userId;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;


    /** 发布的用户信息 */
    private ContentUserInfo userInfo;
    /** 点赞数量 */
    private Integer likeCount;
    /** 宠物标签 */
    private WxPetListInfo userPet;

}
