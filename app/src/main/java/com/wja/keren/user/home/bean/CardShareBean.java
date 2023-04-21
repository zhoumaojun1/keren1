package com.wja.keren.user.home.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 车辆分享列表
 */
public class CardShareBean implements Serializable {
    private int code;
    private String msg;
    private CardShare data;

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

    public CardShare getData() {
        return data;
    }

    public void setData(CardShare data) {
        this.data = data;
    }

    public static class CardShare implements Serializable {

        private int total;//总数
        private List<CardInfo> list;//分享列表


        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<CardInfo> getList() {
            return list;
        }

        public void setList(List<CardInfo> list) {
            this.list = list;
        }

        public static class CardInfo implements Serializable {
            private int id;//分享ID

            private int user_id;//分享者ID

            private int ebike_id;//车辆ID

            private int share_user_id;//被分享者ID

            private String phone;//手机号 如果传的type 0 是被分享人手机号，1 返回是分享人的

            private String name;//车辆名称

            private String user_name;//用户名称 请求type 0 是被分享人，请求1返回分享人

            private int share_time;//分享时长

            private int status;//分享状态

            private String photo;//车辆图片

            private int created_at;//
            private int updated_at;//

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public int getEbike_id() {
                return ebike_id;
            }

            public void setEbike_id(int ebike_id) {
                this.ebike_id = ebike_id;
            }

            public int getShare_user_id() {
                return share_user_id;
            }

            public void setShare_user_id(int share_user_id) {
                this.share_user_id = share_user_id;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public int getShare_time() {
                return share_time;
            }

            public void setShare_time(int share_time) {
                this.share_time = share_time;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getPhoto() {
                return photo;
            }

            public void setPhoto(String photo) {
                this.photo = photo;
            }

            public int getCreated_at() {
                return created_at;
            }

            public void setCreated_at(int created_at) {
                this.created_at = created_at;
            }

            public int getUpdated_at() {
                return updated_at;
            }

            public void setUpdated_at(int updated_at) {
                this.updated_at = updated_at;
            }
        }
    }
}
