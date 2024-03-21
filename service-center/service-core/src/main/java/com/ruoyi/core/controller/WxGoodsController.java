package com.ruoyi.core.controller;

import com.ruoyi.common.core.domain.GlobalResult;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.core.domain.GoodsCar;
import com.ruoyi.core.domain.vo.GoodsListInfo;
import com.ruoyi.core.service.WxGoodsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 小程序——用户
 *
 * @author cocochimp
 * @date 2024-03-12
 */
@RestController
@RequestMapping("/wx/goods")
public class WxGoodsController extends BaseController {

    @Autowired
    private WxGoodsService wxGoodsService;

    /**
     * 展示商品列表(0分类/1秒杀)
     */
    @GetMapping("/showGoodsList")
    @Operation(summary = "通过type种类查询宠物名称", security = {@SecurityRequirement(name = "Authorization")})
    public TableDataInfo showGoodsList(@RequestParam String OperateType,
                                       @RequestParam(value = "categoryId",required = false) int categoryId){
        List<GoodsListInfo> goodsListInfos;
        if("0".equals(OperateType)) goodsListInfos = wxGoodsService.showGoodsList(OperateType,categoryId);
        else if("1".equals(OperateType)) goodsListInfos = wxGoodsService.showGoodsList(OperateType,0);
        else goodsListInfos=new ArrayList<>();

        return getDataTable(goodsListInfos);
    }

    /**
     * 获得Category列表所有种类
     */
    @GetMapping("/showCategoryInfo")
    @Operation(summary = "获得Category列表所有种类", security = {@SecurityRequirement(name = "Authorization")})
    public TableDataInfo showCategoryInfo(){
        return getDataTable(wxGoodsService.showCategoryInfo());
    }

    /**
     * 查看商品详情
     */
    @GetMapping("/showGoodsDetailById")
    @Operation(summary = "查看商品详情", security = {@SecurityRequirement(name = "Authorization")})
    public TableDataInfo showGoodsDetailById(@RequestParam("goodId") Integer goodId){
        return getDataTable(wxGoodsService.showGoodsDetailById(goodId));
    }

    /**
     * 购物车List
     */
    @GetMapping("/showCarListByUserId")
    @Operation(summary = "购物车List", security = {@SecurityRequirement(name = "Authorization")})
    public TableDataInfo showCarListByUserId(@RequestParam String uId){
        return getDataTable(wxGoodsService.showCarListByUserId(uId));
    }

    /**
     * 加入购物车
     */
    @PostMapping("/addCarShop")
    @Operation(summary = "加入购物车", security = {@SecurityRequirement(name = "Authorization")})
    public GlobalResult showCarListByUserId(@RequestBody GoodsCar goodsCar){
        boolean res = wxGoodsService.addCarShop(goodsCar);
        return GlobalResult.ok(res);
    }


    /**
     * 订单地址列表
     */
    @GetMapping("/showLocationByParentId")
    @Operation(summary = "订单地址列表", security = {@SecurityRequirement(name = "Authorization")})
    public TableDataInfo showLocationByParentId(@RequestParam String parentId){
        return getDataTable(wxGoodsService.showLocationByParentId(parentId));
    }



}








