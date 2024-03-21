package com.ruoyi.core.service.impl;

import com.ruoyi.core.domain.*;
import com.ruoyi.core.domain.vo.*;
import com.ruoyi.core.mapper.WxGoodsMapper;
import com.ruoyi.core.service.WxGoodsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
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

    @Override
    public List<GoodsCategory> showCategoryInfo() {
        return wxGoodsMapper.showCategoryInfo();
    }

    @Override
    public List<Goods> showGoodsDetailById(Integer goodId) {
        return wxGoodsMapper.showGoodsDetailById(goodId);
    }

    @Override
    public List<CarGoodsListInfo> showCarListByUserId(String uId) {
        return wxGoodsMapper.showCarListByUserId(uId);
    }

    @Override
    public boolean addCarShop(GoodsCar goodsCar) {
        List<CarGoodsListInfo> carGoodsListInfos = wxGoodsMapper.showCarListByUserId(goodsCar.getUid());
        if(carGoodsListInfos==null) return false;
        boolean isExistShop = carGoodsListInfos.stream()
                .anyMatch(carGoodsListInfo -> carGoodsListInfo.getPId().equals(goodsCar.getPid()));
        //判断购物车是否有该数据
        if(!isExistShop){
//            goodsCar.setUid("'"+goodsCar.getUid()+"'");
            int addCarShopRes = wxGoodsMapper.addCarShop(goodsCar.getPid(),goodsCar.getUid(),goodsCar.getNum());
            return addCarShopRes>0;
        } else{
            if(goodsCar.getNum()>0) wxGoodsMapper.updateCarNum(goodsCar.getPid(),goodsCar.getUid(), goodsCar.getNum());
            else wxGoodsMapper.deleteCarShop(String.valueOf(goodsCar.getPid()), goodsCar.getUid());
        }
        return true;
    }

    @Override
    public int deleteCarGoods(List<String> pIdList, String uId) {
        if(pIdList==null || pIdList.size()==0) return 0;
        long collect = pIdList.stream()
                .filter(Objects::nonNull)
                .map(pId -> wxGoodsMapper.deleteCarShop(pId,uId))
                .filter(res->res>0)
                .count();
        return (int) collect;
    }

    @Override
    public List<ChinaLocation> showLocationByParentId(String parentId) {
        return wxGoodsMapper.showLocationByParentId(parentId);
    }

    @Override
    public int buyGoods(GoodsOrderInfo goodsOrderInfo) {
        List<GoodsPrice> goodsPriceList = goodsOrderInfo.getGoodsPriceList();
        long collect = goodsPriceList.stream()
                .filter(goodsPrice -> goodsPrice.getProductId() != null)
                .map(goodsPrice -> {
                    GoodsOrder goodsOrder = new GoodsOrder();
                    BeanUtils.copyProperties(goodsOrderInfo, goodsOrder);
                    BeanUtils.copyProperties(goodsPrice, goodsOrder);
                    return wxGoodsMapper.buyGoods(goodsOrder);
                }).filter(res->res>0)
                .count();
        return (int) collect;
    }

    @Override
    public List<GoodsOrderListRes> showOrderListByStatus(String status, String userId) {
        if(status==null || userId==null) return new ArrayList<>();
        List<GoodsOrderList> goodsOrderLists = wxGoodsMapper.showOrderListByStatus(status, userId);

        return goodsOrderLists.stream()
                .map(goodsOrderList -> {
                    String province = wxGoodsMapper.showLocationById(String.valueOf(goodsOrderList.getAddProv()));
                    String city = wxGoodsMapper.showLocationById(String.valueOf(goodsOrderList.getAddCity()));
                    String address = goodsOrderList.getAddress();
                    //Bean拷贝返回结果
                    GoodsOrderListRes goodsOrderListRes = new GoodsOrderListRes();
                    BeanUtils.copyProperties(goodsOrderList, goodsOrderListRes);
                    if(!province.equals(city)) goodsOrderListRes.setGoodsAddressList(province + "省," + city + "市," +  address);
                    else goodsOrderListRes.setGoodsAddressList(province + "市," +  address);
                    return goodsOrderListRes;
                }).collect(Collectors.toList());
    }

}
