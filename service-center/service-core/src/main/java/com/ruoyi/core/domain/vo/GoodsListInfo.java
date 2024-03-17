package com.ruoyi.core.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商品信息 Goods
 *
 * @author cocochimp
 * @date 2024-01-31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsListInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 产品id */
    private Long id;

    /** 产品名称 */
    private String name;

    /** 图片组 */
    private String coverPhoto;

    /** 分类id */
    private Long categoryId;

    /** 分类名称 */
    private String category;

    /** 原价 */
    private BigDecimal price;

    /** 优惠价 */
    private BigDecimal amount;

    /** 是否秒杀商品（0不是/1是） */
    private Integer isSale;
}