package com.ruoyi.core.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsAddress implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 省id */
    private Integer addProv;

    /** 市id */
    private Integer addCity;

    /** 地址详情 */
    private String address;

}
