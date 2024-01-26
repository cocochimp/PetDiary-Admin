package com.ruoyi.core.enums;

/**
 * 内容类型
 * 
 * @author cocochimp
 */
public enum ContentType
{
    PICTURE("0" , "图文"), VIDEO("1" , "视频");

    private final String code;
    private final String info;

    ContentType(String code, String info)
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
