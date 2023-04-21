package com.wja.keren.user.home.bean;

import java.io.Serializable;
import java.util.List;

public class CardRunListBean implements Serializable {
    private String code;
    private AllList data;
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public AllList getData() {
        return data;
    }

    public void setData(AllList data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class AllList{
        private int total;
        private List<OneList> list;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<OneList> getList() {
            return list;
        }

        public void setList(List<OneList> list) {
            this.list = list;
        }
        public static class OneList implements Serializable{
            private int id;
            private int user_id;//用户id
            private int ebike_id;//车辆id

            private String start_name;//导航开始地点

            private Start_coordinate start_coordinate;//导航开始坐标

            private String end_name;//骑行停止时间（时间戳）
            private End_coordinate end_coordinate;//导航结束坐标 (结束坐标导航的还是车辆行驶最终的)
            private List<Track_list> track_list;//辆行驶轨迹

            private int mileage;//里程（米）

            private int avg_speed;//平均时速（米）

            private int max_speed;//最高时速（米）

            private int start_time;//骑行开始时间（时间戳）
            private int end_time;//骑行停止时间（时间戳）
            private int created_at;//创建时间

            private int updated_at;//更新时间


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

            public String getStart_name() {
                return start_name;
            }

            public void setStart_name(String start_name) {
                this.start_name = start_name;
            }

            public Start_coordinate getStart_coordinate() {
                return start_coordinate;
            }

            public void setStart_coordinate(Start_coordinate start_coordinate) {
                this.start_coordinate = start_coordinate;
            }

            public String getEnd_name() {
                return end_name;
            }

            public void setEnd_name(String end_name) {
                this.end_name = end_name;
            }

            public End_coordinate getEnd_coordinate() {
                return end_coordinate;
            }

            public void setEnd_coordinate(End_coordinate end_coordinate) {
                this.end_coordinate = end_coordinate;
            }

            public List<Track_list> getTrack_list() {
                return track_list;
            }

            public void setTrack_list(List<Track_list> track_list) {
                this.track_list = track_list;
            }

            public int getMileage() {
                return mileage;
            }

            public void setMileage(int mileage) {
                this.mileage = mileage;
            }

            public int getAvg_speed() {
                return avg_speed;
            }

            public void setAvg_speed(int avg_speed) {
                this.avg_speed = avg_speed;
            }

            public int getMax_speed() {
                return max_speed;
            }

            public void setMax_speed(int max_speed) {
                this.max_speed = max_speed;
            }

            public long getStart_time() {
                return start_time;
            }

            public void setStart_time(int start_time) {
                this.start_time = start_time;
            }

            public long getEnd_time() {
                return end_time;
            }

            public void setEnd_time(int end_time) {
                this.end_time = end_time;
            }

            public long getCreated_at() {
                return created_at;
            }

            public void setCreated_at(int created_at) {
                this.created_at = created_at;
            }

            public long getUpdated_at() {
                return updated_at;
            }

            public void setUpdated_at(int updated_at) {
                this.updated_at = updated_at;
            }
        }
        public static   class Track_list implements Serializable{
            private int lat;
            private int lng;

            public int getLat() {
                return lat;
            }

            public void setLat(int lat) {
                this.lat = lat;
            }

            public int getLng() {
                return lng;
            }

            public void setLng(int lng) {
                this.lng = lng;
            }
        }
        public static  class Start_coordinate  implements Serializable{
            private int lat;
            private int lng;

            public int getLat() {
                return lat;
            }

            public void setLat(int lat) {
                this.lat = lat;
            }

            public int getLng() {
                return lng;
            }

            public void setLng(int lng) {
                this.lng = lng;
            }
        }
        public static  class End_coordinate  implements Serializable{
            private int lat;
            private int lng;

            public int getLat() {
                return lat;
            }

            public void setLat(int lat) {
                this.lat = lat;
            }

            public int getLng() {
                return lng;
            }

            public void setLng(int lng) {
                this.lng = lng;
            }
        }
    }



}
