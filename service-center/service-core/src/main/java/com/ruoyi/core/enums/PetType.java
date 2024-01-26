package com.ruoyi.core.enums;

/**
 * 宠物类型
 * 
 * @author cocochimp
 */
public enum PetType
{
    CAT("0", "猫"), DOG("1", "狗");

    private final String code;
    private final String info;

    PetType(String code, String info)
    {
        this.code = code;
        this.info = info;
    }

    public String getCode()
    {
        return code;
    }

    public String getInfo()
    {
        return info;
    }
}
