package com.ruoyi.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 购物车信息 GoodsCar
 *
 * @author cocochimp
 * @date 2024-01-31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsCar implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 购物车id */
    private Long id;

    /** 商品ID */
    private Long pId;

    /** 用户ID */
    private String uId;

    /** 数量 */
    private Integer num;

    /** 删除标志（0代表存在 2代表删除） */
    private Character delFlag;
}