/**
  * Copyright 2023 json.cn 
  */
package com.wja.keren.user.home.bean;

/**
 * Auto-generated: 2023-03-26 13:43:23
 *
 * @author zhoumaojun
 *
 */
public class FindPasswordBean {

    private int code;
    private User data;
    private String msg;
    public void setCode(int code) {
         this.code = code;
     }
     public int getCode() {
         return code;
     }

    public void setData(User data) {
         this.data = data;
     }
     public User getData() {
         return data;
     }

    public void setMsg(String msg) {
         this.msg = msg;
     }
     public String getMsg() {
         return msg;
     }

}