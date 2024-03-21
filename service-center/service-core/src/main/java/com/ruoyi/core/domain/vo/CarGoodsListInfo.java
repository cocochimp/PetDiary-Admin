package com.ruoyi.core.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 商品信息 Goods
 *
 * @author cocochimp
 * @date 2024-01-31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarGoodsListInfo extends GoodsListInfo implements Serializable{
    private static final long serialVersionUID = 1L;

    /** 产品id */
    private Long PId;

    /** 产品id */
    private Long num;
}