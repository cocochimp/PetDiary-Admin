package com.ruoyi.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 商品类别 GoodsCategory
 * 
 * @author cocochimp
 * @date 2024-01-31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsCategory implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 商品类别id */
    private int id;

    /** 商品类别名称 */
    private String name;

}
