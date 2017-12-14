package com.xxl.job.commons;

import java.io.Serializable;

/**
 * @author listening
 * @description Response:
 * @create 2017 04 13 16:22.
 */
public class Response<T> implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 响应码
   */
  private int code = 200;

  /**
   * 响应信息
   */
  private String msg;

  /**
   * 数据载体
   */
  private T body;

  public Response() {
  }

  public Response(int code, String errmsg) {
    this.code = code;
    this.msg = errmsg;
  }

  public Response(int code, String errmsg, T body) {
    this(code, errmsg);
    this.body = body;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public T getBody() {
    return body;
  }

  public void setBody(T body) {
    this.body = body;
  }
}
