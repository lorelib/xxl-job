package com.xxl.job.api.restful;

import com.xxl.job.admin.core.model.XxlJobInfo;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

/**
 * @author listening
 * @description
 * @date 2017-12-13 17:40
 * @since 1.0
 */
public class JobInfoRestApiTest extends SpringbootJUnitTests {
  @Test
  public void testAdd() throws Exception {
    XxlJobInfo jobInfo = new XxlJobInfo();
    jobInfo.setJobGroup(1);
    jobInfo.setJobDesc("autoEnquiryStatisPerWeek");
    jobInfo.setExecutorRouteStrategy("FIRST");
    jobInfo.setJobCron("0 0 1 ? * MON");
    jobInfo.setGlueType("BEAN");
    jobInfo.setExecutorHandler("AutoEnquriy");
    jobInfo.setExecutorBlockStrategy("SERIAL_EXECUTION");
    jobInfo.setExecutorFailStrategy("FAIL_ALARM");
    jobInfo.setAuthor("listening");

    MvcResult ret = mockMvc.perform(
        post("/jobInfo")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(toString(jobInfo))
    ).andReturn();

    System.out.println("------------------" + ret.getResponse().getContentAsString());
  }
}
