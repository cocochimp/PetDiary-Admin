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
public class WxPetListInfo
{
    private static final long serialVersionUID = 1L;

    /** 宠物id */
    private String petId;

    /** 宠物姓名 */
    private String name;

    /** 封面图片url */
    private String img;

    /** 性格 */
    private String character;

    /** 种类type */
    private int type;
}
