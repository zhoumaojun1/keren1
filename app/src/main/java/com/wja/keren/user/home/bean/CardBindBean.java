/**
  * Copyright 2023 json.cn 
  */
package com.wja.keren.user.home.bean;

/**
 * Auto-generated: 2023-03-26 13:47:43
 *
 *@author zhoumaojun

 */
public class CardBindBean {

    private int code;
    private CardScanCodeInfo data;
    private String msg;
    public void setCode(int code) {
         this.code = code;
     }
     public int getCode() {
         return code;
     }

    public void setData(CardScanCodeInfo data) {
         this.data = data;
     }
     public CardScanCodeInfo getData() {
         return data;
     }

    public void setMsg(String msg) {
         this.msg = msg;
     }
     public String getMsg() {
         return msg;
     }


     public static class CardScanCodeInfo{
         private String image;
         private String frame_code;
         private String name;
         private int is_bind;

         public String getImage() {
             return image;
         }

         public void setImage(String image) {
             this.image = image;
         }

         public String getFrame_code() {
             return frame_code;
         }

         public void setFrame_code(String frame_code) {
             this.frame_code = frame_code;
         }

         public String getName() {
             return name;
         }

         public void setName(String name) {
             this.name = name;
         }

         public int getIs_bind() {
             return is_bind;
         }

         public void setIs_bind(int is_bind) {
             this.is_bind = is_bind;
         }
     }
}