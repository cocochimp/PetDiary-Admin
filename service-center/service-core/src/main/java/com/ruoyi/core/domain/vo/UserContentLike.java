package com.ruoyi.core.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserContentLike implements Serializable {
    private static final long serialVersionUID = 1L;
    /** 喜欢ID */
    private Long likeId;

    /** 点赞用户ID */
    private String userId;

    /** 内容ID */
    private Long contentId;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;
}