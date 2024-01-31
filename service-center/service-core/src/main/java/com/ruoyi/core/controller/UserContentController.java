package com.ruoyi.core.controller;

import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.security.annotation.Log;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.common.security.enums.BusinessType;
import com.ruoyi.core.domain.UserContent;
import com.ruoyi.core.domain.vo.ContentInfo;
import com.ruoyi.core.service.IUserContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 图文管理Controller
 *
 * @author cocochimp
 * @date 2023-12-04
 */
@RestController
@RequestMapping("/picture")
public class UserContentController extends BaseController {
    @Autowired
    private IUserContentService userPictureService;

    /**
     * 查询图文管理列表
     */
    @RequiresPermissions("picture:picture:list")
    @GetMapping("/list")
    public TableDataInfo list(UserContent userContent) {
        startPage();
        List<UserContent> list = userPictureService.selectUserPictureList(userContent);
        return getDataTable(list);
    }

    /**
     * 导出图文管理列表
     */
    @RequiresPermissions("picture:picture:export")
    @Log(title = "图文管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, UserContent userContent) {
        List<UserContent> list = userPictureService.selectUserPictureList(userContent);
        ExcelUtil<UserContent> util = new ExcelUtil<UserContent>(UserContent.class);
        util.exportExcel(response, list, "图文管理数据");
    }

    /**
     * 获取图文管理详细信息
     */
    @RequiresPermissions("picture:picture:query")
    @GetMapping(value = "/{contentId}")
    public AjaxResult getInfo(@PathVariable("contentId") Long contentId) {
        return success(userPictureService.selectUserPictureByContentId(contentId));
    }

    /**
     * 新增图文管理
     */
    @RequiresPermissions("picture:picture:add")
    @Log(title = "图文管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UserContent userContent) {
        return toAjax(userPictureService.insertUserPicture(userContent));
    }

    /**
     * 修改图文管理
     */
    @RequiresPermissions("picture:picture:edit")
    @Log(title = "图文管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UserContent userContent) {
        return toAjax(userPictureService.updateUserPicture(userContent));
    }

    /**
     * 删除图文管理
     */
    @RequiresPermissions("picture:picture:remove")
    @Log(title = "图文管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{contentIds}")
    public AjaxResult remove(@PathVariable Long[] contentIds) {
        return toAjax(userPictureService.deleteUserPictureByContentIds(contentIds));
    }

    /**
     * 修改视频状态
     */
    @RequiresPermissions("picture:picture:ban")
    @Log(title = "图文管理", businessType = BusinessType.UPDATE)
    @PutMapping("/ban")
    public AjaxResult ban(@RequestBody UserContent userContent) {
        UserContent userContentInDB = userPictureService.selectUserPictureByContentId(userContent.getContentId());
        if (!Objects.equals(userContentInDB.getStatus(), userContent.getStatus())
                || !Objects.equals(userContentInDB.getRejectInfo(), userContent.getRejectInfo())) {
            userContent.setUpdateTime(new Date());
        }
        return toAjax(userPictureService.updateUserPicture(userContent));
    }

    /**
     * 查看所有content内容
     */
    @PostMapping("/showContentInfo")
    public TableDataInfo showContentInfo() {
        startPage();
        List<ContentInfo> contentInfos = userPictureService.showAllContentInfo();
        return getDataTable(contentInfos);
    }
}
