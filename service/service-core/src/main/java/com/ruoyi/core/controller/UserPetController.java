package com.ruoyi.core.controller;

import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.security.annotation.Log;
import com.ruoyi.common.security.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.core.domain.UserPet;
import com.ruoyi.core.service.IUserPetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 宠物类别Controller
 * 
 * @author ruoyi
 * @date 2023-12-04
 */
@RestController
@RequestMapping("/pet")
public class UserPetController extends BaseController
{
    @Autowired
    private IUserPetService userPetService;

    /**
     * 查询宠物类别列表
     */
    @RequiresPermissions("pet:pet:list")
    @GetMapping("/list")
    public TableDataInfo list(UserPet userPet)
    {
        startPage();
        List<UserPet> list = userPetService.selectUserPetList(userPet);
        return getDataTable(list);
    }

    /**
     * 导出宠物类别列表
     */
    @RequiresPermissions("pet:pet:export")
    @Log(title = "宠物类别", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, UserPet userPet)
    {
        List<UserPet> list = userPetService.selectUserPetList(userPet);
        ExcelUtil<UserPet> util = new ExcelUtil<UserPet>(UserPet.class);
        util.exportExcel(response, list, "宠物类别数据");
    }

    /**
     * 获取宠物类别详细信息
     */
    @RequiresPermissions("pet:pet:query")
    @GetMapping(value = "/{petId}")
    public AjaxResult getInfo(@PathVariable("petId") String petId)
    {
        return success(userPetService.selectUserPetByPetId(petId));
    }

    /**
     * 新增宠物类别
     */
    @RequiresPermissions("pet:pet:add")
    @Log(title = "宠物类别", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UserPet userPet)
    {
        return toAjax(userPetService.insertUserPet(userPet));
    }

    /**
     * 修改宠物类别
     */
    @RequiresPermissions("pet:pet:edit")
    @Log(title = "宠物类别", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UserPet userPet)
    {
        return toAjax(userPetService.updateUserPet(userPet));
    }

    /**
     * 删除宠物类别
     */
    @RequiresPermissions("pet:pet:remove")
    @Log(title = "宠物类别", businessType = BusinessType.DELETE)
	@DeleteMapping("/{petIds}")
    public AjaxResult remove(@PathVariable String[] petIds)
    {
        return toAjax(userPetService.deleteUserPetByPetIds(petIds));
    }
}
