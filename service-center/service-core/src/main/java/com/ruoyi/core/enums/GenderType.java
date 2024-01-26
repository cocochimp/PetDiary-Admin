package com.ruoyi.core.enums;

/**
 * 性别分类
 * 
 * @author cocochimp
 */
public enum GenderType
{
    MALE("0", "男"), FEMALE("1", "女"), UNKNOWN("2", "未知");

    private final String code;
    private final String info;

    GenderType(String code, String info)
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
