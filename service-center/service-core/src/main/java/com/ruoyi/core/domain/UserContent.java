package com.ruoyi.core.domain;

import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 图文管理对象 user_picture
 * 
 * @author cocochimp
 * @date 2023-12-04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserContent extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 内容ID */
    private Long contentId;

    /** 标题 */
    @Excel(name = "标题")
    private String title;

    /** 内容描述 */
    @Excel(name = "内容描述")
    private String description;

    /** 内容类型（0图文 1视频） */
    private String contentType;

    /** 图片地址（通过“，”分割） */
    @Excel(name = "图片地址", readConverterExp = "通=过“，”分割")
    private String coverPath;

    /** 视频链接 */
    private String videoPath;

    /** 内容发布人ID */
    @Excel(name = "内容发布人ID")
    private String userId;

    /** 内容状态（0待审批 1通过 2驳回） */
    @Excel(name = "内容状态", readConverterExp = "0=待审批,1=通过,2=驳回")
    private String status;

    /** 驳回内容 */
    private String rejectInfo;

}
