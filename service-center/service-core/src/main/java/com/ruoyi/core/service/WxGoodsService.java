package com.ruoyi.core.service;

import com.ruoyi.core.domain.ChinaLocation;
import com.ruoyi.core.domain.Goods;
import com.ruoyi.core.domain.GoodsCar;
import com.ruoyi.core.domain.GoodsCategory;
import com.ruoyi.core.domain.vo.CarGoodsListInfo;
import com.ruoyi.core.domain.vo.GoodsListInfo;
import com.ruoyi.core.domain.vo.GoodsOrderInfo;
import com.ruoyi.core.domain.vo.GoodsOrderListRes;

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
    List<Goods> showGoodsDetailById(Integer goodId);

    /**
     * 购物车
     */
    List<CarGoodsListInfo> showCarListByUserId(String uId);
    boolean addCarShop(GoodsCar goodsCar);
    int deleteCarGoods(List<String> pIdList,String uId);

    /**
     * 订单
     */
    List<ChinaLocation> showLocationByParentId(String parentId);
    int buyGoods(GoodsOrderInfo goodsOrderInfo);
    List<GoodsOrderListRes> showOrderListByStatus(String status, String userId);


}
