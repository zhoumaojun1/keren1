package com.wja.keren.user.home.bean;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * 车辆列表
 */
public class CardListBean implements Serializable {
    private int code;
    private List<CardList> data =new LinkedList<>();
    private String msg;


    public List<CardList> getData() {
        return data;
    }

    public void setData(List<CardList> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class CardList implements Serializable{
        private String name;//车辆名称
        private String photo;//图片
        private int id;//ID
        private int  share_time;//分享时间
        private int  socket_type;//链接类型 0 蓝牙1 4G
        private String   frame_code;//车架号
        private String  engine_code;//电机号
        private String   sn_code;//sn
        private String   color;//颜色
        private int   is_manager;//是否是车主
        private int   created_at;//创建时间

        public int getShare_time() {
            return share_time;
        }

        public void setShare_time(int share_time) {
            this.share_time = share_time;
        }

        public int getSocket_type() {
            return socket_type;
        }

        public void setSocket_type(int socket_type) {
            this.socket_type = socket_type;
        }

        public String getFrame_code() {
            return frame_code;
        }

        public void setFrame_code(String frame_code) {
            this.frame_code = frame_code;
        }

        public String getEngine_code() {
            return engine_code;
        }

        public void setEngine_code(String engine_code) {
            this.engine_code = engine_code;
        }

        public String getSn_code() {
            return sn_code;
        }

        public void setSn_code(String sn_code) {
            this.sn_code = sn_code;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public int getIs_manager() {
            return is_manager;
        }

        public void setIs_manager(int is_manager) {
            this.is_manager = is_manager;
        }

        public int getCreated_at() {
            return created_at;
        }

        public void setCreated_at(int created_at) {
            this.created_at = created_at;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
