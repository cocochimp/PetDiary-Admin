package com.ruoyi.common.redis.constants;

/**
 * 通用常量信息
 * 
 * @author cocochimp
 */
public class Constants
{
    /**
     * 自动识别json对象白名单配置（仅允许解析的包名，范围越小越安全）
     */
    public static final String[] JSON_WHITELIST_STR = { "org.springframework", "com.ruoyi" };

}
