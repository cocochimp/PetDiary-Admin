package com.ruoyi.core.service.impl;

import com.ruoyi.core.domain.Goods;
import com.ruoyi.core.domain.GoodsCategory;
import com.ruoyi.core.domain.vo.GoodsListInfo;
import com.ruoyi.core.mapper.WxGoodsMapper;
import com.ruoyi.core.service.WxGoodsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Goods业务层
 * 
 * @author cocochimp
 * @date 2023-12-04
 */
@Service
public class WxGoodsServiceImpl implements WxGoodsService
{
    @Autowired
    private WxGoodsMapper wxGoodsMapper;

    @Override
    public List<GoodsListInfo> showGoodsList(String OperateType, int categoryId) {
        List<Goods> goods;
        List<GoodsCategory> goodsCategories = wxGoodsMapper.showCategoryInfo();
        if("0".equals(OperateType)) goods = wxGoodsMapper.showGoodsListByCategoryId(categoryId);
        else if("1".equals(OperateType)) goods = wxGoodsMapper.showSaleGoodsList(10);
        else return new ArrayList<>();

        //bean拷贝
        List<GoodsListInfo> goodsDTO = goods.stream()
                .map(good -> {
                    GoodsListInfo goodsListInfo = new GoodsListInfo();
                    BeanUtils.copyProperties(good,goodsListInfo);
                    return goodsListInfo;
                })
                .collect(Collectors.toList());
        //传递category的name
        goodsDTO.forEach(good ->
                goodsCategories.stream()
                        .filter(category -> category.getId() == good.getCategoryId())
                        .findFirst()
                        .ifPresent(category -> good.setCategory(category.getName())));
        return goodsDTO;
    }




}
