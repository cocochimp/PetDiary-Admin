package com.ruoyi.core.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * content对象
 *
 * @author cocochimp
 * @date 2024-01-31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailInfo
{
    private static final long serialVersionUID = 1L;

    /** 用户ID */
    private String userId;
    /** 发布的用户信息 */
    private ContentUserInfo userInfo;
    /** 点赞数 */
    private Integer likeCount;
    /** 粉丝 */
    private Integer fanCount;
    /** 关注 */
    private Integer attentionCount;
}
