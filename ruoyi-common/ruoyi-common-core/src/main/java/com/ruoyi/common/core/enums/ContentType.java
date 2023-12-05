package com.ruoyi.common.core.enums;

/**
 * 用户状态
 * 
 * @author ruoyi
 */
public enum ContentType
{
    PICTURE("0"), VIDEO("1");

    private final String code;

    ContentType(String code)
    {
        this.code = code;
    }

    public String getCode()
    {
        return code;
    }
}
