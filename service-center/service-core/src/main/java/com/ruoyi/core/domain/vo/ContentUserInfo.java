package com.ruoyi.core.domain.vo;

import com.ruoyi.common.core.annotation.Excel;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 用户列表对象 user_info
 * 
 * @author cocochimp
 * @date 2023-12-04
 */
@Data
public class ContentUserInfo
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("nickname", getNickname())
            .append("openid", getOpenid())
            .append("avatar", getAvatar())
            .append("gender", getGender())
            .append("brief", getBrief())
            .toString();
    }
}
