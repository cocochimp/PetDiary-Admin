package com.ruoyi.core.mapper;

import com.ruoyi.core.constant.MapperConstant;
import com.ruoyi.core.domain.ChinaLocation;
import com.ruoyi.core.domain.Goods;
import com.ruoyi.core.domain.GoodsCategory;
import com.ruoyi.core.domain.GoodsOrder;
import com.ruoyi.core.domain.vo.CarGoodsListInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
            "where isSale=1 " +
            "limit #{limit} ")
    List<Goods> showSaleGoodsList(@Param("limit") Integer limit);

    /*
        购物车模块
     */

    /*购物车列表*/
    @Select("select goods_car.id id,pId,name,coverPhoto,categoryId,price,amount,num,isSale " +
            "from goods,goods_car " +
            "where goods.id=goods_car.pId and goods_car.del_flag=0 and uId=#{uId}  ")
    List<CarGoodsListInfo> showCarListByUserId(@Param("uId") String uId);

    /*加入购物车*/
    /*如果没有商品*/
    @Insert("insert into goods_car(pId,uId,num) " +
            "values(#{pid},#{uid},#{num})  ")
    int addCarShop(@Param("pid") Long pid,@Param("uid") String uid,@Param("num") Integer num);

    /*如果有商品，修改num>0时*/
    @Update("update goods_car " +
            "set num=#{num} " +
            "where pId=#{pid} and uId=#{uid} and "+ MapperConstant.del_flag)
    int updateCarNum(@Param("pid") Long pid,@Param("uid") String uid,@Param("num") Integer num);

    /*如果有商品，修改num=0时*/
    @Update("update goods_car " +
            "set " + MapperConstant.no_del_flag +
            "where pId in (#{pid}) and uId=#{uid} and "+ MapperConstant.del_flag)
    int deleteCarShop(@Param("pid") String pid,@Param("uid") String uid);


    /*
        订单模块
     */

    /*地址列表*/
    @Select("select id,name " +
            "from china_locations " +
            "where parentId=#{parentId} ")
    List<ChinaLocation> showLocationByParentId(@Param("parentId") String parentId);

    /*购买商品*/
    @Insert("insert into `goods_order` (`userId`, `pId`, `amount`, `num`, `receiverName`, `receiverPhone`, `addProv`, `addCity`, `address`) " +
            "values (#{userId}, #{productId}, #{amount}, #{num}, #{receiverName}, #{receiverPhone}, #{addProv},#{addCity}, #{address})")
    int buyGoods(GoodsOrder goodsOrder);

}
