package com.ruoyi.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商品订单信息 GoodsOrder
 *
 * @author cocochimp
 * @date 2024-01-31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsOrder implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 订单id */
    private Long id;

    /** 支付单号id */
    private String paySn;

    /** 用户id */
    private String userId;

    /** 产品id */
    private Long productId;

    /** 优惠后价格 */
    private BigDecimal amount;

    /** 商品数量 */
    private Integer num;

    /** 购买时间 */
    private LocalDateTime addTime;

    /** 订单状态{0未知  1待发货  2 已发货  3 已收货  4 完成交易} */
    private Integer status;

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

    /** 标志客户是否有发起退款 1 申请退款  2 已退款 */
    private String back;

    /** 退款原因 */
    private String backRemark;

    /** 申请退款时间 */
    private LocalDateTime backAddtime;
}