package com.ruoyi.common.core.enums;

/**
 * 内容状态
 * 
 * @author ruoyi
 */
public enum ContentStatus
{
    EXAMINE("0", "待审批"), OK("1", "通过"), REJECT("2", "驳回");

    private final String code;
    private final String info;

    ContentStatus(String code, String info)
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
