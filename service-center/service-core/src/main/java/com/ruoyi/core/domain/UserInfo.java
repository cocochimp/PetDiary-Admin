package com.ruoyi.core.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 用户列表对象 user_info
 * 
 * @author cocochimp
 * @date 2023-12-04
 */
@Data
public class UserInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** openid */
    @Excel(name = "openid")
    private String openid;


    /** 微信名称 */
    @Excel(name = "微信名称")
    private String nickname;

    /** 头像地址 */
    @Excel(name = "头像地址")
    private String avatar;

    /** 性别（0男 1女 2未知) */
    @Excel(name = "性别", readConverterExp = "性别（0男 1女 2未知)")
    private Integer gender;

    /** 个人简介 */
    private String brief;

    /** session */
    @Excel(name = "session")
    private String sessionKey;


    /** 用户状态（0正常 1封禁 2拉黑） */
    @Excel(name = "用户状态", readConverterExp = "0=正常,1=封禁,2=拉黑")
    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("nickname", getNickname())
            .append("openid", getOpenid())
            .append("sessionKey", getSessionKey())
            .append("avatar", getAvatar())
            .append("gender", getGender())
            .append("brief", getBrief())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
