package com.ruoyi.core.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsPrice implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 产品id */
    private Long productId;

    /** 优惠后价格 */
    private BigDecimal amount;

    /** 商品数量 */
    private Integer num;

}
