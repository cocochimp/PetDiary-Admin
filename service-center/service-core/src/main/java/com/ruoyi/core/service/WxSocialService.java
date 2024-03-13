package com.ruoyi.core.service;

import com.ruoyi.core.domain.UserPet;
import com.ruoyi.core.domain.vo.WxNationInfo;
import com.ruoyi.core.domain.vo.WxPetListInfo;

import java.util.List;

/**
 * 用户列表
 * 
 * @author cocochimp
 * @date 2023-12-04
 */
public interface WxSocialService
{

    /**
     * 展示所有content的内容
     */
    List<WxNationInfo> showNationNumByType(int type);

    /**
     * 展示所有宠物信息
     */
    List<WxPetListInfo> showListByNation(int type,String nation);

    /**
     * 展示宠物详细信息
     */
    List<UserPet> showPetDetailByPetId(int petId);

}
