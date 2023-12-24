package com.ruoyi.core.service;

import com.ruoyi.core.domain.UserPet;

import java.util.List;

/**
 * 宠物类别Service接口
 * 
 * @author ruoyi
 * @date 2023-12-04
 */
public interface IUserPetService 
{
    /**
     * 查询宠物类别
     * 
     * @param petId 宠物类别主键
     * @return 宠物类别
     */
    public UserPet selectUserPetByPetId(String petId);

    /**
     * 查询宠物类别列表
     * 
     * @param userPet 宠物类别
     * @return 宠物类别集合
     */
    public List<UserPet> selectUserPetList(UserPet userPet);

    /**
     * 新增宠物类别
     * 
     * @param userPet 宠物类别
     * @return 结果
     */
    public int insertUserPet(UserPet userPet);

    /**
     * 修改宠物类别
     * 
     * @param userPet 宠物类别
     * @return 结果
     */
    public int updateUserPet(UserPet userPet);

    /**
     * 批量删除宠物类别
     * 
     * @param petIds 需要删除的宠物类别主键集合
     * @return 结果
     */
    public int deleteUserPetByPetIds(String[] petIds);

    /**
     * 删除宠物类别信息
     * 
     * @param petId 宠物类别主键
     * @return 结果
     */
    public int deleteUserPetByPetId(String petId);
}
