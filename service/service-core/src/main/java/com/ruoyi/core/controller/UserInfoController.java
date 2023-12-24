package com.ruoyi.core.controller;

import com.ruoyi.common.core.entity.UserBan;
import com.ruoyi.common.core.entity.UserBanExample;
import com.ruoyi.common.core.entity.UserBlock;
import com.ruoyi.common.core.entity.UserBlockExample;
import com.ruoyi.common.core.mapper.UserBanMapper;
import com.ruoyi.common.core.mapper.UserBlockMapper;
import com.ruoyi.core.domain.UserInfo;
import com.ruoyi.core.service.IUserInfoService;
import com.ruoyi.common.core.enums.DelFlag;
import com.ruoyi.common.core.enums.UserInfoStatus;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.security.annotation.Log;
import com.ruoyi.common.security.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户列表Controller
 *
 * @author ruoyi
 * @date 2023-12-04
 */
@RestController
@RequestMapping("/user")
public class UserInfoController extends BaseController
{
    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private UserBanMapper userBanMapper;

    @Autowired
    private UserBlockMapper userBlockMapper;

    /**
     * 查询用户列表列表
     */
    @RequiresPermissions("user:user:list")
    @GetMapping("/list")
    public TableDataInfo list(UserInfo userInfo)
    {
        startPage();
        List<UserInfo> list = userInfoService.selectUserInfoList(userInfo);
        return getDataTable(list);
    }

    /**
     * 导出用户列表列表
     */
    @RequiresPermissions("user:user:export")
    @Log(title = "用户列表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, UserInfo userInfo)
    {
        List<UserInfo> list = userInfoService.selectUserInfoList(userInfo);
        ExcelUtil<UserInfo> util = new ExcelUtil<UserInfo>(UserInfo.class);
        util.exportExcel(response, list, "用户列表数据");
    }

    /**
     * 获取用户列表详细信息
     */
    @RequiresPermissions("user:user:query")
    @GetMapping(value = "/{userId}")
    public AjaxResult getInfo(@PathVariable("userId") Long userId)
    {
        return success(userInfoService.selectUserInfoByUserId(userId));
    }

    /**
     * 新增用户列表
     */
    @RequiresPermissions("user:user:add")
    @Log(title = "用户列表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UserInfo userInfo)
    {
        return toAjax(userInfoService.insertUserInfo(userInfo));
    }

    /**
     * 修改用户列表
     */
    @RequiresPermissions("user:user:edit")
    @Log(title = "用户列表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UserInfo userInfo)
    {
        return toAjax(userInfoService.updateUserInfo(userInfo));
    }

    /**
     * 删除用户列表
     */
    @RequiresPermissions("user:user:remove")
    @Log(title = "用户列表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{userIds}")
    public AjaxResult remove(@PathVariable Long[] userIds)
    {
        return toAjax(userInfoService.deleteUserInfoByUserIds(userIds));
    }

    /**
     * 修改用户列表状态
     */
    @RequiresPermissions("user:user:ban")
    @Log(title = "用户列表", businessType = BusinessType.UPDATE)
    @PutMapping("/ban")
    public AjaxResult ban(@RequestBody UserInfo userInfo)
    {
        if (userInfo.getStatus() == UserInfoStatus.BAN.getCode()) {
            UserBanExample userBanExample = new UserBanExample();
            UserBanExample.Criteria criteria = userBanExample.createCriteria();
            criteria.andOpenidEqualTo(userInfo.getOpenid());
            UserBan userBan = new UserBan();
            userBan.setOpenid(userInfo.getOpenid());
            userBan.setBanTime(LocalDateTime.now());
            userBan.setRemoveBanTime(LocalDateTime.now().plusDays(2));
            userBan.setUpdateTime(LocalDateTime.now());
            userBan.setDelFlag(DelFlag.EXIST.getCode());
            userBan.setRemark(userInfo.getRemark());
            if (!userBanMapper.selectByExample(userBanExample).isEmpty()){
                userBanMapper.updateByExampleSelective(userBan, userBanExample);
            } else {
                userBan.setCreateTime(LocalDateTime.now());
                userBanMapper.insertSelective(userBan);
            }
        }
        // TODO: 优化下block逻辑
        else if (userInfo.getStatus() == UserInfoStatus.BLOCK.getCode()) {
            UserBlockExample userBlockExample = new UserBlockExample();
            UserBlockExample.Criteria criteria = userBlockExample.createCriteria();
            criteria.andOpenidEqualTo(userInfo.getOpenid());
            UserBlock userBlock = new UserBlock();
            userBlock.setOpenid(userInfo.getOpenid());
            userBlock.setUpdateTime(LocalDateTime.now());
            userBlock.setDelFlag(DelFlag.EXIST.getCode());
            userBlock.setRemark(userInfo.getRemark());
            if (!userBlockMapper.selectByExample(userBlockExample).isEmpty()){
                userBlockMapper.updateByExampleSelective(userBlock, userBlockExample);
            } else {
                userBlock.setCreateTime(LocalDateTime.now());
                userBlockMapper.insertSelective(userBlock);
            }
        }
        userInfo.setRemark(null);
        return toAjax(userInfoService.updateUserInfo(userInfo));
    }

}
