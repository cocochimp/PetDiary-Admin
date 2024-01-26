package com.ruoyi.core.enums;

/**
 * 用户信息状态
 * 
 * @author cocochimp
 */
public enum UserInfoStatus
{
    OK("0", "正常"), BAN("1", "封禁"), BLOCK("2", "拉黑");

    private final String code;
    private final String info;

    UserInfoStatus(String code, String info)
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
