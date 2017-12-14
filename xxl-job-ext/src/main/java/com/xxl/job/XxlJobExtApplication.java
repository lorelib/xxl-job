package com.xxl.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ImportResource;

/**
 * @author listening
 * @description
 * @date 2017-12-13 17:25
 * @since 1.0
 */
@SpringBootApplication
@ImportResource("classpath:spring/*.xml")
/*@ImportResource({
    "classpath:spring/applicationcontext-base.xml",
    "classpath:spring/applicationcontext-xxl-job-admin.xml"})*/
public class XxlJobExtApplication extends SpringBootServletInitializer {
  public static void main(String[] args) {
    SpringApplication.run(XxlJobExtApplication.class, args);
  }

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
    return builder.sources(XxlJobExtApplication.class);
  }
}
