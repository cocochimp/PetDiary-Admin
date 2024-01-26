package com.ruoyi.core.controller;

import com.ruoyi.common.core.enums.UserInfoStatus;
import com.ruoyi.core.domain.UserComment;
import com.ruoyi.core.domain.UserPicture;
import com.ruoyi.core.service.IUserCommentService;
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

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 评论管理Controller
 *
 * @author cocochimp
 * @date 2023-12-05
 */
@RestController
@RequestMapping("/comment")
public class UserCommentController extends BaseController
{
    @Autowired
    private IUserCommentService userCommentService;

    /**
     * 查询评论管理列表
     */
    @RequiresPermissions("comment:comment:list")
    @GetMapping("/list")
    public TableDataInfo list(UserComment userComment)
    {
        startPage();
        List<UserComment> list = userCommentService.selectUserCommentList(userComment);
        return getDataTable(list);
    }

    /**
     * 导出评论管理列表
     */
    @RequiresPermissions("comment:comment:export")
    @Log(title = "评论管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, UserComment userComment)
    {
        List<UserComment> list = userCommentService.selectUserCommentList(userComment);
        ExcelUtil<UserComment> util = new ExcelUtil<UserComment>(UserComment.class);
        util.exportExcel(response, list, "评论管理数据");
    }

    /**
     * 获取评论管理详细信息
     */
    @RequiresPermissions("comment:comment:query")
    @GetMapping(value = "/{commentId}")
    public AjaxResult getInfo(@PathVariable("commentId") Long commentId)
    {
        return success(userCommentService.selectUserCommentByCommentId(commentId));
    }

    /**
     * 新增评论管理
     */
    @RequiresPermissions("comment:comment:add")
    @Log(title = "评论管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UserComment userComment)
    {
        return toAjax(userCommentService.insertUserComment(userComment));
    }

    /**
     * 修改评论管理
     */
    @RequiresPermissions("comment:comment:edit")
    @Log(title = "评论管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UserComment userComment)
    {
        return toAjax(userCommentService.updateUserComment(userComment));
    }

    /**
     * 删除评论管理
     */
    @RequiresPermissions("comment:comment:remove")
    @Log(title = "评论管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{commentIds}")
    public AjaxResult remove(@PathVariable Long[] commentIds)
    {
        return toAjax(userCommentService.deleteUserCommentByCommentIds(commentIds));
    }

    /**
     * 修改视频状态
     */
    @RequiresPermissions("comment:comment:ban")
    @Log(title = "评论管理", businessType = BusinessType.UPDATE)
    @PutMapping("/ban")
    public AjaxResult ban(@RequestBody UserComment userComment)
    {
        UserComment userCommentInDB = userCommentService.selectUserCommentByCommentId(userComment.getCommentId());
        if (!Objects.equals(userComment.getStatus(), userCommentInDB.getStatus())
        || !Objects.equals(userCommentInDB.getRejectInfo(), userComment.getRejectInfo())) {
            userComment.setUpdateTime(new Date());
        }
        return toAjax(userCommentService.updateUserComment(userComment));
    }
}
