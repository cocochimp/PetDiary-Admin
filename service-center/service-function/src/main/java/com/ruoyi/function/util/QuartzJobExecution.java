package com.ruoyi.function.util;

import org.quartz.JobExecutionContext;

import com.ruoyi.function.domain.SysJob;

/**
 * 定时任务处理（允许并发执行）
 * 
 * @author cocochimp
 *
 */
public class QuartzJobExecution extends AbstractQuartzJob
{
    @Override
    protected void doExecute(JobExecutionContext context, SysJob sysJob) throws Exception
    {
        JobInvokeUtil.invokeMethod(sysJob);
    }
}
