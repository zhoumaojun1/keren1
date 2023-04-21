package com.wja.keren.user.home.bean;

import java.io.Serializable;
import java.util.List;

public class LoginInfoBean implements Serializable {

    private  int code;
    private String msg;
    private UserInfo data;

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

    public UserInfo getData() {
        return data;
    }

    public void setData(UserInfo data) {
        this.data = data;
    }

    public static class UserInfo implements Serializable{

        private String name;
        private String phone;
        private int status;
        private String picture;
        private String token;
        private List<Integer> roles;
        private List<String> role_names;
        private String type;//用户类型 "100"普通app车辆用户 "010"经销商用户 "001"后台管理用户 "110"车辆用户和经销商用户 "101"车辆用户和后台管理用户

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public List<Integer> getRoles() {
            return roles;
        }

        public void setRoles(List<Integer> roles) {
            this.roles = roles;
        }

        public List<String> getRole_names() {
            return role_names;
        }

        public void setRole_names(List<String> role_names) {
            this.role_names = role_names;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

}
