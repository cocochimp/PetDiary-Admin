package com.ruoyi.core.constant;

/**
 * mapper语句内容
 *
 * @author cocochimp
 */
public class MapperConstant {
    /**
     * 逻辑删除
     */
    public static final String del_flag=" del_flag=0 "; //正常状态
    public static final String no_del_flag=" del_flag=2 "; //删除状态

    /**
     * 正常状态
     */
    public static final String status=" status=0 ";

    /**
     * 按照时间排序
     */
    public static final String orderByUpdateTime=" update_time desc";

    /**
     * 返回list数量
     */
    public static final int pageSize=10; //数据返回默认大小

    /**
     * 返回list数量
     */
    public static final int pageHotSize=5; //返回宠物热榜数据
    public static final int StartPage=1; //初始页面

    /**
     * 订单是否退货
     */
    public static final String isBack=" back='0' "; //返回宠物热榜数据
}