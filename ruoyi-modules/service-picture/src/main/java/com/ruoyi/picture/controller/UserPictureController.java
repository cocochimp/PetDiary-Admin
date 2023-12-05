package com.ruoyi.picture.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.core.enums.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.picture.domain.UserPicture;
import com.ruoyi.picture.service.IUserPictureService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 图文管理Controller
 * 
 * @author ruoyi
 * @date 2023-12-04
 */
@RestController
@RequestMapping("/picture")
public class UserPictureController extends BaseController
{
    @Autowired
    private IUserPictureService userPictureService;

    /**
     * 查询图文管理列表
     */
    @RequiresPermissions("picture:picture:list")
    @GetMapping("/list")
    public TableDataInfo list(UserPicture userPicture)
    {
        startPage();
        List<UserPicture> list = userPictureService.selectUserPictureList(userPicture);
        return getDataTable(list);
    }

    /**
     * 导出图文管理列表
     */
    @RequiresPermissions("picture:picture:export")
    @Log(title = "图文管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, UserPicture userPicture)
    {
        List<UserPicture> list = userPictureService.selectUserPictureList(userPicture);
        ExcelUtil<UserPicture> util = new ExcelUtil<UserPicture>(UserPicture.class);
        util.exportExcel(response, list, "图文管理数据");
    }

    /**
     * 获取图文管理详细信息
     */
    @RequiresPermissions("picture:picture:query")
    @GetMapping(value = "/{contentId}")
    public AjaxResult getInfo(@PathVariable("contentId") Long contentId)
    {
        return success(userPictureService.selectUserPictureByContentId(contentId));
    }

    /**
     * 新增图文管理
     */
    @RequiresPermissions("picture:picture:add")
    @Log(title = "图文管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UserPicture userPicture)
    {
        return toAjax(userPictureService.insertUserPicture(userPicture));
    }

    /**
     * 修改图文管理
     */
    @RequiresPermissions("picture:picture:edit")
    @Log(title = "图文管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UserPicture userPicture)
    {
        return toAjax(userPictureService.updateUserPicture(userPicture));
    }

    /**
     * 删除图文管理
     */
    @RequiresPermissions("picture:picture:remove")
    @Log(title = "图文管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{contentIds}")
    public AjaxResult remove(@PathVariable Long[] contentIds)
    {
        return toAjax(userPictureService.deleteUserPictureByContentIds(contentIds));
    }
}
