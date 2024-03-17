package com.ruoyi.core.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户列表对象 user_info
 * 
 * @author cocochimp
 * @date 2023-12-04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContentUserInfo
{
    private static final long serialVersionUID = 1L;

    /** openid */
    private String openid;

    /** 微信名称 */
    private String nickname;

    /** 头像地址 */
    private String avatar;

    /** 性别（0男 1女 2未知) */
    private Integer gender;

    /** 个人简介 */
    private String brief;
}
