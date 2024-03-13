package com.ruoyi.core.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 国家内容
 *
 * @author cocochimp
 * @date 2024-01-31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WxNationInfo
{
    private static final long serialVersionUID = 1L;

    /** 内容ID */
    private String nationName;

    /** 标题 */
    private String number;

}
