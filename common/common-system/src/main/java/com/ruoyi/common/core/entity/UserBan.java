package com.ruoyi.common.core.entity;

import java.time.LocalDateTime;

public class UserBan {
    private Long banId;

    private String openid;

    private LocalDateTime banTime;

    private LocalDateTime removeBanTime;

    private String delFlag;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String remark;

    public Long getBanId() {
        return banId;
    }

    public void setBanId(Long banId) {
        this.banId = banId;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public LocalDateTime getBanTime() {
        return banTime;
    }

    public void setBanTime(LocalDateTime banTime) {
        this.banTime = banTime;
    }

    public LocalDateTime getRemoveBanTime() {
        return removeBanTime;
    }

    public void setRemoveBanTime(LocalDateTime removeBanTime) {
        this.removeBanTime = removeBanTime;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag == null ? null : delFlag.trim();
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}