/**
  * Copyright 2023 json.cn 
  */
package com.wja.keren.user.home.bean;

/**
 * Auto-generated: 2023-03-26 13:39:38
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
public class User {

    private String name;
    private String picture;
    private int status;
    private String phone;
    private int id;
    private Roles roles;
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

    public void setRoles(Roles roles) {
         this.roles = roles;
     }
     public Roles getRoles() {
         return roles;
     }

     public class Roles{


     }

}