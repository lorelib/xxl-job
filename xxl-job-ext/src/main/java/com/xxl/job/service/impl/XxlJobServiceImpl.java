package com.xxl.job.service.impl;

import com.xxl.job.admin.core.enums.ExecutorFailStrategyEnum;
import com.xxl.job.admin.core.model.XxlJobGroup;
import com.xxl.job.admin.core.model.XxlJobInfo;
import com.xxl.job.admin.core.route.ExecutorRouteStrategyEnum;
import com.xxl.job.admin.core.schedule.XxlJobDynamicScheduler;
import com.xxl.job.admin.dao.XxlJobGroupDao;
import com.xxl.job.admin.dao.XxlJobInfoDao;
import com.xxl.job.service.XxlJobService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.enums.ExecutorBlockStrategyEnum;
import com.xxl.job.core.glue.GlueTypeEnum;
import org.apache.commons.lang.StringUtils;
import org.quartz.CronExpression;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.MessageFormat;

/**
 * @author listening
 * @description
 * @date 2017-12-13 17:25
 * @since 1.0
 */
@Service("xxlJobServiceExt")
public class XxlJobServiceImpl implements XxlJobService {
  private static Logger logger = LoggerFactory.getLogger(XxlJobServiceImpl.class);

  @Resource
  private XxlJobGroupDao xxlJobGroupDao;
  @Resource
  private XxlJobInfoDao xxlJobInfoDao;

  @Override
  public ReturnT<XxlJobInfo> add(XxlJobInfo jobInfo) {
    // valid
    XxlJobGroup group = xxlJobGroupDao.load(jobInfo.getJobGroup());
    if (group == null) {
      return new ReturnT(ReturnT.FAIL_CODE, "请选择“执行器”");
    }
    if (!CronExpression.isValidExpression(jobInfo.getJobCron())) {
      return new ReturnT(ReturnT.FAIL_CODE, "请输入格式正确的“Cron”");
    }
    if (StringUtils.isBlank(jobInfo.getJobDesc())) {
      return new ReturnT(ReturnT.FAIL_CODE, "请输入“任务描述”");
    }
    if (StringUtils.isBlank(jobInfo.getAuthor())) {
      return new ReturnT(ReturnT.FAIL_CODE, "请输入“负责人”");
    }
    if (ExecutorRouteStrategyEnum.match(jobInfo.getExecutorRouteStrategy(), null) == null) {
      return new ReturnT(ReturnT.FAIL_CODE, "路由策略非法");
    }
    if (ExecutorBlockStrategyEnum.match(jobInfo.getExecutorBlockStrategy(), null) == null) {
      return new ReturnT(ReturnT.FAIL_CODE, "阻塞处理策略非法");
    }
    if (ExecutorFailStrategyEnum.match(jobInfo.getExecutorFailStrategy(), null) == null) {
      return new ReturnT(ReturnT.FAIL_CODE, "失败处理策略非法");
    }
    if (GlueTypeEnum.match(jobInfo.getGlueType()) == null) {
      return new ReturnT(ReturnT.FAIL_CODE, "运行模式非法非法");
    }
    if (GlueTypeEnum.BEAN == GlueTypeEnum.match(jobInfo.getGlueType()) && StringUtils.isBlank(jobInfo.getExecutorHandler())) {
      return new ReturnT(ReturnT.FAIL_CODE, "请输入“JobHandler”");
    }

    // fix "\r" in shell
    if (GlueTypeEnum.GLUE_SHELL == GlueTypeEnum.match(jobInfo.getGlueType()) && jobInfo.getGlueSource() != null) {
      jobInfo.setGlueSource(jobInfo.getGlueSource().replaceAll("\r", ""));
    }

    // childJobKey valid
    if (StringUtils.isNotBlank(jobInfo.getChildJobKey())) {
      String[] childJobKeys = jobInfo.getChildJobKey().split(",");
      for (String childJobKeyItem : childJobKeys) {
        String[] childJobKeyArr = childJobKeyItem.split("_");
        if (childJobKeyArr.length != 2) {
          return new ReturnT(ReturnT.FAIL_CODE, MessageFormat.format("子任务Key({0})格式错误", childJobKeyItem));
        }
        XxlJobInfo childJobInfo = xxlJobInfoDao.loadById(Integer.valueOf(childJobKeyArr[1]));
        if (childJobInfo == null) {
          return new ReturnT(ReturnT.FAIL_CODE, MessageFormat.format("子任务Key({0})无效", childJobKeyItem));
        }
      }
    }

    // add in db
    xxlJobInfoDao.save(jobInfo);
    if (jobInfo.getId() < 1) {
      return new ReturnT(ReturnT.FAIL_CODE, "新增任务失败");
    }

    // add in quartz
    String qz_group = String.valueOf(jobInfo.getJobGroup());
    String qz_name = String.valueOf(jobInfo.getId());
    try {
      XxlJobDynamicScheduler.addJob(qz_name, qz_group, jobInfo.getJobCron());
      //XxlJobDynamicScheduler.pauseJob(qz_name, qz_group);
      ReturnT ret = new ReturnT(ReturnT.SUCCESS_CODE, null);
      ret.setContent(jobInfo);
      return ret;
    } catch (SchedulerException e) {
      logger.error(e.getMessage(), e);
      try {
        xxlJobInfoDao.delete(jobInfo.getId());
        XxlJobDynamicScheduler.removeJob(qz_name, qz_group);
      } catch (SchedulerException e1) {
        logger.error(e.getMessage(), e1);
      }
      return new ReturnT(ReturnT.FAIL_CODE, "新增任务失败:" + e.getMessage());
    }
  }
}
