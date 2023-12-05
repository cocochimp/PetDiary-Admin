package com.ruoyi.pet.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.pet.mapper.UserPetMapper;
import com.ruoyi.pet.domain.UserPet;
import com.ruoyi.pet.service.IUserPetService;

/**
 * 宠物类别Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-12-04
 */
@Service
public class UserPetServiceImpl implements IUserPetService 
{
    @Autowired
    private UserPetMapper userPetMapper;

    /**
     * 查询宠物类别
     * 
     * @param petId 宠物类别主键
     * @return 宠物类别
     */
    @Override
    public UserPet selectUserPetByPetId(String petId)
    {
        return userPetMapper.selectUserPetByPetId(petId);
    }

    /**
     * 查询宠物类别列表
     * 
     * @param userPet 宠物类别
     * @return 宠物类别
     */
    @Override
    public List<UserPet> selectUserPetList(UserPet userPet)
    {
        return userPetMapper.selectUserPetList(userPet);
    }

    /**
     * 新增宠物类别
     * 
     * @param userPet 宠物类别
     * @return 结果
     */
    @Override
    public int insertUserPet(UserPet userPet)
    {
        return userPetMapper.insertUserPet(userPet);
    }

    /**
     * 修改宠物类别
     * 
     * @param userPet 宠物类别
     * @return 结果
     */
    @Override
    public int updateUserPet(UserPet userPet)
    {
        return userPetMapper.updateUserPet(userPet);
    }

    /**
     * 批量删除宠物类别
     * 
     * @param petIds 需要删除的宠物类别主键
     * @return 结果
     */
    @Override
    public int deleteUserPetByPetIds(String[] petIds)
    {
        return userPetMapper.deleteUserPetByPetIds(petIds);
    }

    /**
     * 删除宠物类别信息
     * 
     * @param petId 宠物类别主键
     * @return 结果
     */
    @Override
    public int deleteUserPetByPetId(String petId)
    {
        return userPetMapper.deleteUserPetByPetId(petId);
    }
}
