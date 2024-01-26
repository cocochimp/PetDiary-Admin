package com.ruoyi.core.enums;

/**
 * 直播间用户状态
 * 
 * @author cocochimp
 */
public enum LiveUserStatus
{
    ONLINE("0", "在线"), DOWN("1", "下线");

    private final String code;
    private final String info;

    LiveUserStatus(String code, String info)
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
