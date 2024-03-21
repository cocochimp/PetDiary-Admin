package com.ruoyi.core.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 商品订单信息 GoodsOrder
 *
 * @author cocochimp
 * @date 2024-01-31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsOrderInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 用户id */
    private String userId;

    /** 产品id */
    private List<GoodsPrice> goodsPriceList;

    /** 收货人 */
    private String receiverName;

    /** 联系电话 */
    private String receiverPhone;

    /** 省id */
    private Integer addProv;

    /** 市id */
    private Integer addCity;

    /** 地址详情 */
    private String address;
}