package com.xxl.job.api.restful;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

/**
 * @author listening
 * @description
 * @date 2017-11-27 15:36
 * @since 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootJUnitTests {
  @Autowired
  private WebApplicationContext applicationContext;
  protected MockMvc mockMvc;
  private ObjectMapper mapper = new ObjectMapper();

  @Before
  public void setup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.applicationContext).build();
  }

  public String toString(Object obj) throws IOException {
    return mapper.writeValueAsString(obj);
  }
}
