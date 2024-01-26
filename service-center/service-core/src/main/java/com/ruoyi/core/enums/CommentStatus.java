package com.ruoyi.core.enums;

/**
 * 评论状态
 * 
 * @author cocochimp
 */
public enum CommentStatus
{
    OK("0", "正常"), REJECT("1", "驳回");

    private final String code;
    private final String info;

    CommentStatus(String code, String info)
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
