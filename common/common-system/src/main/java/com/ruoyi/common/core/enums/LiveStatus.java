package com.ruoyi.common.core.enums;

/**
 * 直播状态
 * 
 * @author cocochimp
 */
public enum LiveStatus
{
    OK("0", "正常"), BAN("1", "封禁"), END("2", "结束");

    private final String code;
    private final String info;

    LiveStatus(String code, String info)
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
