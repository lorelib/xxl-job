package com.xxl.job.api.restful;

import com.xxl.job.admin.core.model.XxlJobInfo;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.service.XxlJobService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author listening
 * @description
 * @date 2017-12-13 17:04
 * @since 1.0
 */
@RestController
@RequestMapping("jobInfo")
public class JobInfoRestApi {
  @Resource
  private XxlJobService xxlJobService;

  @PostMapping("")
  @ResponseBody
  public ReturnT<XxlJobInfo> add(@RequestBody XxlJobInfo jobInfo) {
    return xxlJobService.add(jobInfo);
  }
}
