package com.ruoyi.user.domain;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.ruoyi.common.core.web.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "封禁时间表")
public class UserBan extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private Long banId;

    @ApiModelProperty(value = "被封禁用户openid")
    private String openid;

    @ApiModelProperty(value = "封禁的时间")
    private Date banTime;

    @ApiModelProperty(value = "解除封禁的时间")
    private Date removeBanTime;

    @ApiModelProperty(value = "手动解除标志（0代表存在 2代表删除）")
    private String delFlag;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "备注")
    private String remark;


}

