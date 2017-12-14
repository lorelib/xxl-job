package com.xxl.job.service;


import com.xxl.job.admin.core.model.XxlJobInfo;
import com.xxl.job.commons.Response;
import com.xxl.job.core.biz.model.ReturnT;

import java.io.Serializable;

/**
 * @author listening
 * @description
 * @date 2017-12-13 17:25
 * @since 1.0
 */
public interface XxlJobService {
	
	Response<XxlJobInfo> add(XxlJobInfo jobInfo);
}
