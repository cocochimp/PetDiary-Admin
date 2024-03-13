package com.ruoyi.core.service;

import com.ruoyi.core.domain.vo.ContentInfo;

import java.util.List;

/**
 * 用户列表
 * 
 * @author cocochimp
 * @date 2023-12-04
 */
public interface WxHomeService
{

    /**
     * 展示所有content的内容
     */
    List<ContentInfo> showAllContentInfo();

    /**
     * 展示所有content的内容（最新updateTime）
     */
    List<ContentInfo> showAllContentInfoOrderByUpdateTime();

    /**
     * 展示所有content的内容（热榜like）
     */
    List<ContentInfo> showAllContentInfoOrderByLikeCount();

}
