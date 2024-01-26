package com.ruoyi.core.controller;

import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.security.annotation.Log;
import com.ruoyi.common.security.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.core.domain.UserLive;
import com.ruoyi.core.service.IUserLiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 直播Controller
 *
 * @author cocochimp
 * @date 2023-12-04
 */
@RestController
@RequestMapping("/live")
public class UserLiveController extends BaseController
{
    @Autowired
    private IUserLiveService userLiveService;

    /**
     * 查询直播列表
     */
    @RequiresPermissions("live:live:list")
    @GetMapping("/list")
    public TableDataInfo list(UserLive userLive)
    {
        startPage();
        List<UserLive> list = userLiveService.selectUserLiveList(userLive);
        return getDataTable(list);
    }

    /**
     * 导出直播列表
     */
    @RequiresPermissions("live:live:export")
    @Log(title = "直播", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, UserLive userLive)
    {
        List<UserLive> list = userLiveService.selectUserLiveList(userLive);
        ExcelUtil<UserLive> util = new ExcelUtil<UserLive>(UserLive.class);
        util.exportExcel(response, list, "直播数据");
    }

    /**
     * 获取直播详细信息
     */
    @RequiresPermissions("live:live:query")
    @GetMapping(value = "/{liveId}")
    public AjaxResult getInfo(@PathVariable("liveId") Long liveId)
    {
        return success(userLiveService.selectUserLiveByLiveId(liveId));
    }

    /**
     * 新增直播
     */
    @RequiresPermissions("live:live:add")
    @Log(title = "直播", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UserLive userLive)
    {
        return toAjax(userLiveService.insertUserLive(userLive));
    }

    /**
     * 修改直播
     */
    @RequiresPermissions("live:live:edit")
    @Log(title = "直播", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UserLive userLive)
    {
        return toAjax(userLiveService.updateUserLive(userLive));
    }

    /**
     * 删除直播
     */
    @RequiresPermissions("live:live:remove")
    @Log(title = "直播", businessType = BusinessType.DELETE)
	@DeleteMapping("/{liveIds}")
    public AjaxResult remove(@PathVariable Long[] liveIds)
    {
        return toAjax(userLiveService.deleteUserLiveByLiveIds(liveIds));
    }

    /**
     * 修改直播状态
     */
    @RequiresPermissions("live:live:ban")
    @Log(title = "直播", businessType = BusinessType.UPDATE)
    @PutMapping("/ban")
    public AjaxResult ban(@RequestBody UserLive userLive)
    {
        UserLive userLiveInDB = userLiveService.selectUserLiveByLiveId(userLive.getLiveId());
        if (!Objects.equals(userLive.getStatus(), userLiveInDB.getStatus())
        || !Objects.equals(userLive.getBanInfo(), userLiveInDB.getBanInfo())) {
            userLive.setUpdateTime(new Date());
        }
        return toAjax(userLiveService.updateUserLive(userLive));
    }
}
