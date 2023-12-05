package com.ruoyi.picture.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 图文管理对象 user_picture
 * 
 * @author ruoyi
 * @date 2023-12-04
 */
public class UserPicture extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 内容ID */
    private Long contentId;

    /** 标题 */
    @Excel(name = "标题")
    private String title;

    /** 内容描述 */
    @Excel(name = "内容描述")
    private String description;

    /** 内容类型（0图文 1视频） */
    private String contentType;

    /** 图片地址（通过“，”分割） */
    @Excel(name = "图片地址", readConverterExp = "通=过“，”分割")
    private String coverPath;

    /** 视频链接 */
    private String videoPath;

    /** 分类ID */
    @Excel(name = "分类ID")
    private Long categoryId;

    /** 内容发布人ID */
    @Excel(name = "内容发布人ID")
    private Long userId;

    /** 内容状态（0待审批 1通过 2驳回） */
    @Excel(name = "内容状态", readConverterExp = "0=待审批,1=通过,2=驳回")
    private String status;

    /** 驳回内容 */
    private String rejectInfo;

    public void setContentId(Long contentId) 
    {
        this.contentId = contentId;
    }

    public Long getContentId() 
    {
        return contentId;
    }
    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getTitle() 
    {
        return title;
    }
    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }
    public void setContentType(String contentType) 
    {
        this.contentType = contentType;
    }

    public String getContentType() 
    {
        return contentType;
    }
    public void setCoverPath(String coverPath) 
    {
        this.coverPath = coverPath;
    }

    public String getCoverPath() 
    {
        return coverPath;
    }
    public void setVideoPath(String videoPath) 
    {
        this.videoPath = videoPath;
    }

    public String getVideoPath() 
    {
        return videoPath;
    }
    public void setCategoryId(Long categoryId) 
    {
        this.categoryId = categoryId;
    }

    public Long getCategoryId() 
    {
        return categoryId;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setRejectInfo(String rejectInfo) 
    {
        this.rejectInfo = rejectInfo;
    }

    public String getRejectInfo() 
    {
        return rejectInfo;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("contentId", getContentId())
            .append("title", getTitle())
            .append("description", getDescription())
            .append("contentType", getContentType())
            .append("coverPath", getCoverPath())
            .append("videoPath", getVideoPath())
            .append("categoryId", getCategoryId())
            .append("userId", getUserId())
            .append("status", getStatus())
            .append("rejectInfo", getRejectInfo())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
