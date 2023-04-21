/**
  * Copyright 2023 json.cn 
  */
package com.wja.keren.user.home.bean;

import java.io.Serializable;

/**
 * Auto-generated: 2023-03-26 13:39:38
 *
 * @author zhoumaojun
 *
 */
public class UserInfoBean implements Serializable {

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
    public static class User implements Serializable {

        private String name;
        private String picture;
        private int status;
        private String phone;
        private int id;
        private com.wja.keren.user.home.bean.User.Roles roles;
        public void setName(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }
        public String getPicture() {
            return picture;
        }

        public void setStatus(int status) {
            this.status = status;
        }
        public int getStatus() {
            return status;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
        public String getPhone() {
            return phone;
        }

        public void setId(int id) {
            this.id = id;
        }
        public int getId() {
            return id;
        }

        public void setRoles(com.wja.keren.user.home.bean.User.Roles roles) {
            this.roles = roles;
        }
        public com.wja.keren.user.home.bean.User.Roles getRoles() {
            return roles;
        }

        public class Roles{


        }

    }
}