//package com.ruoyi.core.domain;
//
//import com.baomidou.mybatisplus.annotation.IdType;
//import com.baomidou.mybatisplus.annotation.TableField;
//import com.baomidou.mybatisplus.annotation.TableId;
//import com.baomidou.mybatisplus.annotation.TableName;
//import lombok.Data;
//import org.springframework.format.annotation.DateTimeFormat;
//
//import java.util.Date;
//
///**
// * @author lastwhisper
// * @desc
// * @email gaojun56@163.com
// */
//@Data
//@TableName("user")
//public class WechatUserInfo {
//    private static final long serialVersionUID = 1L;
//    /**
//     * open_id
//     */
//    @TableId(value = "open_id",type = IdType.INPUT)
//    private String openId;
//    /**
//     * 头像
//     */
//    @TableField("avatar_url")
//    private String avatarUrl;
//    /**
//     * 性别
//     */
//    @TableField("gender")
//    private Integer gender;
//    /**
//     * 网名
//     */
//    @TableField("nick_name")
//    private String nickName;
//    /**
//     * 个性签名
//     */
//    @TableField("signature")
//    private String signature;
//    /**
//     * session_key
//     */
//    @TableField("session_key")
//    private String sessionKey;
//    /**
//     * skey
//     */
//    private String skey;
//    /**
//     * 创建时间
//     */
//    @TableField("create_time")
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    private Date createTime;
//    /**
//     * 最后登录时间
//     */
//    @TableField("last_visit_time")
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    private Date lastVisitTime;
//
//}
