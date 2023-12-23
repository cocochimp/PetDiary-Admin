package com.ruoyi.core.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 用户列表对象 user_info
 * 
 * @author ruoyi
 * @date 2023-12-04
 */
public class UserInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long userId;

    /** 微信名称 */
    @Excel(name = "微信名称")
    private String nickname;

    /** openid */
    @Excel(name = "openid")
    private String openid;

    /** session */
    @Excel(name = "session")
    private String sessionKey;

    /** 头像地址 */
    @Excel(name = "头像地址")
    private String avatar;

    /** 性别（0男 1女 2未知) */
    @Excel(name = "性别", readConverterExp = "性别（0男 1女 2未知)")
    private String gender;

    /** 地址 */
    @Excel(name = "地址")
    private String address;

    /** 个人简介 */
    private String brief;

    /** 用户状态（0正常 1封禁 2拉黑） */
    @Excel(name = "用户状态", readConverterExp = "0=正常,1=封禁,2=拉黑")
    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setNickname(String nickname) 
    {
        this.nickname = nickname;
    }

    public String getNickname() 
    {
        return nickname;
    }
    public void setOpenid(String openid) 
    {
        this.openid = openid;
    }

    public String getOpenid() 
    {
        return openid;
    }
    public void setSessionKey(String sessionKey) 
    {
        this.sessionKey = sessionKey;
    }

    public String getSessionKey() 
    {
        return sessionKey;
    }
    public void setAvatar(String avatar) 
    {
        this.avatar = avatar;
    }

    public String getAvatar() 
    {
        return avatar;
    }
    public void setGender(String gender) 
    {
        this.gender = gender;
    }

    public String getGender() 
    {
        return gender;
    }
    public void setAddress(String address) 
    {
        this.address = address;
    }

    public String getAddress() 
    {
        return address;
    }
    public void setBrief(String brief) 
    {
        this.brief = brief;
    }

    public String getBrief() 
    {
        return brief;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("userId", getUserId())
            .append("nickname", getNickname())
            .append("openid", getOpenid())
            .append("sessionKey", getSessionKey())
            .append("avatar", getAvatar())
            .append("gender", getGender())
            .append("address", getAddress())
            .append("brief", getBrief())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
