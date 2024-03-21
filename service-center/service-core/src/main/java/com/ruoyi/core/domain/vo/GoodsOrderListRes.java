package com.ruoyi.core.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsOrderListRes implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 订单id */
    private Long id;

    /** 支付单号id */
    private String paySn;

    /** 产品id */
    private Long productId;

    /** 优惠后价格 */
    private BigDecimal amount;

    /** 商品数量 */
    private Integer num;

    /** 购买时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date addressTime;

    /** 收货人 */
    private String receiverName;

    /** 联系电话 */
    private String receiverPhone;

    /** 地址信息 */
    private String goodsAddressList;
}