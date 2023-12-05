package com.ruoyi.live.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 直播对象 user_live
 * 
 * @author ruoyi
 * @date 2023-12-04
 */
public class UserLive extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 直播ID */
    private Long liveId;

    /** 标题 */
    @Excel(name = "标题")
    private String title;

    /** 内容描述 */
    @Excel(name = "内容描述")
    private String description;

    /** 直播封面地址 */
    @Excel(name = "直播封面地址")
    private String coverPath;

    /** 内容发布人ID */
    @Excel(name = "内容发布人ID")
    private Long userId;

    /** 类别ID */
    @Excel(name = "类别ID")
    private Long categoryId;

    /** 直播状态（0正常 1封禁） */
    @Excel(name = "直播状态", readConverterExp = "0=正常,1=封禁")
    private String status;

    /** 封禁内容 */
    private String banInfo;

    public void setLiveId(Long liveId) 
    {
        this.liveId = liveId;
    }

    public Long getLiveId() 
    {
        return liveId;
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
    public void setCoverPath(String coverPath) 
    {
        this.coverPath = coverPath;
    }

    public String getCoverPath() 
    {
        return coverPath;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setCategoryId(Long categoryId) 
    {
        this.categoryId = categoryId;
    }

    public Long getCategoryId() 
    {
        return categoryId;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setBanInfo(String banInfo) 
    {
        this.banInfo = banInfo;
    }

    public String getBanInfo() 
    {
        return banInfo;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("liveId", getLiveId())
            .append("title", getTitle())
            .append("description", getDescription())
            .append("coverPath", getCoverPath())
            .append("userId", getUserId())
            .append("categoryId", getCategoryId())
            .append("status", getStatus())
            .append("banInfo", getBanInfo())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
