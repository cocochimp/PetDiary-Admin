package com.ruoyi.core.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAttentionRes implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 用户ID */
    private String userId;

    /** 被关注用户ID */
    private String attentionUserId;

}