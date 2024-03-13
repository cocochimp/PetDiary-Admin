package com.ruoyi.core.service.impl;

import com.ruoyi.core.domain.UserPet;
import com.ruoyi.core.domain.vo.WxNationInfo;
import com.ruoyi.core.domain.vo.WxPetListInfo;
import com.ruoyi.core.mapper.WxSocialMapper;
import com.ruoyi.core.service.WxSocialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 宠物类别Service业务层处理
 * 
 * @author cocochimp
 * @date 2023-12-04
 */
@Service
public class WxSocialServiceImpl implements WxSocialService
{
    @Autowired
    private WxSocialMapper wxSocialMapper;


    @Override
    public List<WxNationInfo> showNationNumByType(int type) {
        List<WxNationInfo> wxNationInfos = wxSocialMapper.showNationNumByType(type);
        return wxNationInfos;
    }

    @Override
    public List<WxPetListInfo> showListByNation(int type, String nation) {
        List<WxPetListInfo> wxPetListInfos = wxSocialMapper.showListByNation(type, nation);
        return wxPetListInfos;
    }

    @Override
    public List<UserPet> showPetDetailByPetId(int petId) {
        List<UserPet> userPets = wxSocialMapper.showPetDetailByPetId(petId);
        return userPets;
    }
}
