package com.ruoyi.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商品信息 Goods
 *
 * @author cocochimp
 * @date 2024-01-31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Goods implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 产品id */
    private Long id;

    /** 产品名称 */
    private String name;

    /** 商品详情 */
    private String content;

    /** 图片组 */
    private String coverPhoto;

    /** 产品表 */
    private String detailPhoto;

    /** 分类id */
    private Long categoryId;

    /** 原价 */
    private BigDecimal price;

    /** 优惠价 */
    private BigDecimal amount;

    /** 是否秒杀商品（0不是/1是） */
    private Integer isSale;

    /** 抢购开始时间 */
    private LocalDateTime startTime;

    /** 抢购结束时间 */
    private LocalDateTime endTime;
}