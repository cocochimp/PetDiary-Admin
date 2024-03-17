package com.ruoyi.core.mapper;

import com.ruoyi.core.domain.Goods;
import com.ruoyi.core.domain.GoodsCategory;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 商城Mapper接口
 * 
 * @author cocochimp
 * @date 2024-03-17
 */
public interface WxGoodsMapper
{
    /*
        商品展示模块
     */

    /*根据标签id查标签名称*/
    @Select("select name " +
            "from goods_category " +
            "where id=#{categoryId} ")
    String showCategoryNameById(@Param("categoryId") Integer categoryId);

    /*根据标签id查标签名称*/
    @Select("select * " +
            "from goods_category")
    List<GoodsCategory> showCategoryInfo();

    /*商品展示列表*/
    @Select("select id,name,coverPhoto,price,amount,categoryId,isSale " +
            "from goods " +
            "where goods.categoryId=#{categoryId} ")
    List<Goods> showGoodsListByCategoryId(@Param("categoryId") Integer categoryId);

    /*商品详情*/
    @Select("select id,name,content,coverPhoto,detailPhoto,price,amount,categoryId,isSale " +
            "from goods " +
            "where goods.id=#{goodId} ")
    List<Goods> showGoodsDetailById(@Param("goodId") Integer goodId);

    /*商品展示列表-促销产品*/
    @Select("select id,name,coverPhoto,price,amount,categoryId,isSale " +
            "from goods " +
            "where goods.isSale=1 " +
            "limit #{limit} ")
    List<Goods> showSaleGoodsList(@Param("limit") Integer limit);

    /*
        购物车模块
     */
    /*商品展示列表-促销产品*/
//    @Select("select goods_car.id,pId,name,coverPhoto,price,amount,num,categoryId,isSale " +
//            "from goods,goods_car " +
//            "where goods.id=goods_car.pId and goods_car.del_flag=0 and uId='omyzG6xGFX9OUPIcTHmOdQ8DVSFk'  ")
//    List<Goods> showSaleGoodsList(@Param("limit") Integer limit);










}
