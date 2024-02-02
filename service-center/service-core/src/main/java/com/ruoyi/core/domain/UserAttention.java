package com.ruoyi.core.domain;

import com.ruoyi.common.core.web.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 关注表 user_attention
 *
 * @author cocochimp
 * @date 2024-02-01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAttention extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 关注ID */
    private Long attentionId;

    /** 关注人ID */
    private String userId;

    /** 被关注人ID */
    private String attentionUserId;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;
}