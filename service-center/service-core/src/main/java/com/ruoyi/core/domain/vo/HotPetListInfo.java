package com.ruoyi.core.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 热榜内容
 *
 * @author cocochimp
 * @date 2024-01-31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotPetListInfo
{
    private static final long serialVersionUID = 1L;

    /** 宠物id */
    private String petId;

    /** 宠物姓名 */
    private String name;

    /** 封面图片url */
    private String img;

    /** 种类type */
    private int type;

    /** 总数sum */
    private int sum;
}
