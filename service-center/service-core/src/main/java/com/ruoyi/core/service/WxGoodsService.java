package com.ruoyi.core.service;

import com.ruoyi.core.domain.GoodsCategory;
import com.ruoyi.core.domain.vo.GoodsListInfo;

import java.util.List;

/**
 * 商品列表
 * 
 * @author cocochimp
 */
public interface WxGoodsService
{

    /**
     * 展示商品列表(0分类/1秒杀)
     */
    List<GoodsListInfo> showGoodsList(String OperateType, int categoryId);


    List<GoodsCategory> showCategoryInfo();

}
