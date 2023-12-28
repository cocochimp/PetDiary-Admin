package com.ruoyi.core.controller;

import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.security.annotation.Log;
import com.ruoyi.common.security.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.core.domain.UserVideo;
import com.ruoyi.core.service.IUserVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 视频管理Controller
 * 
 * @author cocochimp
 * @date 2023-12-04
 */
@RestController
@RequestMapping("/video")
public class UserVideoController extends BaseController
{
    @Autowired
    private IUserVideoService userVideoService;

    /**
     * 查询视频管理列表
     */
    @RequiresPermissions("video:video:list")
    @GetMapping("/list")
    public TableDataInfo list(UserVideo userVideo)
    {
        startPage();
        List<UserVideo> list = userVideoService.selectUserVideoList(userVideo);
        return getDataTable(list);
    }

    /**
     * 导出视频管理列表
     */
    @RequiresPermissions("video:video:export")
    @Log(title = "视频管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, UserVideo userVideo)
    {
        List<UserVideo> list = userVideoService.selectUserVideoList(userVideo);
        ExcelUtil<UserVideo> util = new ExcelUtil<UserVideo>(UserVideo.class);
        util.exportExcel(response, list, "视频管理数据");
    }

    /**
     * 获取视频管理详细信息
     */
    @RequiresPermissions("video:video:query")
    @GetMapping(value = "/{contentId}")
    public AjaxResult getInfo(@PathVariable("contentId") Long contentId)
    {
        return success(userVideoService.selectUserVideoByContentId(contentId));
    }

    /**
     * 新增视频管理
     */
    @RequiresPermissions("video:video:add")
    @Log(title = "视频管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UserVideo userVideo)
    {
        return toAjax(userVideoService.insertUserVideo(userVideo));
    }

    /**
     * 修改视频管理
     */
    @RequiresPermissions("video:video:edit")
    @Log(title = "视频管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UserVideo userVideo)
    {
        return toAjax(userVideoService.updateUserVideo(userVideo));
    }

    /**
     * 删除视频管理
     */
    @RequiresPermissions("video:video:remove")
    @Log(title = "视频管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{contentIds}")
    public AjaxResult remove(@PathVariable Long[] contentIds)
    {
        return toAjax(userVideoService.deleteUserVideoByContentIds(contentIds));
    }
}
