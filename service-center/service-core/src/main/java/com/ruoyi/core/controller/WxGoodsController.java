package com.ruoyi.core.controller;

import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.core.domain.vo.GoodsListInfo;
import com.ruoyi.core.service.WxGoodsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}








