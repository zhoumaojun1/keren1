package com.wja.keren.user.home.bean;

import java.io.Serializable;

public class CardConfigBean implements Serializable {

    private String code;
    private CardConfig data;
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public CardConfig getData() {
        return data;
    }

    public void setData(CardConfig data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class CardConfig implements Serializable{

        private int id;
        private int user_id;
        private int ebike_id;
        private Ebike_set ebike_set;//上坡辅助设置范围时速？（待确认）
        private int hill_assist;//上坡辅助 0 开启 1关闭
        private int hill_assist_range;//上坡辅助设置范围时速？（待确认）
        private int fly_car;//防飞车 0 开启 1关闭
        private Msg_set msg_set;//防盗告警
        private int type;//0 用户 1 车辆
        private long created_at;
        private long updated_at;

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

        public Ebike_set getEbike_set() {
            return ebike_set;
        }

        public void setEbike_set(Ebike_set ebike_set) {
            this.ebike_set = ebike_set;
        }

        public int getHill_assist() {
            return hill_assist;
        }

        public void setHill_assist(int hill_assist) {
            this.hill_assist = hill_assist;
        }

        public int getHill_assist_range() {
            return hill_assist_range;
        }

        public void setHill_assist_range(int hill_assist_range) {
            this.hill_assist_range = hill_assist_range;
        }

        public int getFly_car() {
            return fly_car;
        }

        public void setFly_car(int fly_car) {
            this.fly_car = fly_car;
        }

        public Msg_set getMsg_set() {
            return msg_set;
        }

        public void setMsg_set(Msg_set msg_set) {
            this.msg_set = msg_set;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public long getCreated_at() {
            return created_at;
        }

        public void setCreated_at(long created_at) {
            this.created_at = created_at;
        }

        public long getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(long updated_at) {
            this.updated_at = updated_at;
        }

        public static class Ebike_set  implements Serializable{
            private int power_warn;//电量提醒 0 开启 1 关闭
            private int power_value;//设置提醒值1~99(低于多少执行提醒)
            private int leave_lock;//离车自动锁车 0 开启 1关闭
            private int leave_countdown;//锁车倒计时(秒)
            private int open_lock;//无感开锁 0开启 1 关闭
            private int open_lock_distance;//开锁距离(米)
            private int hitchs_up;//故障上报 0 开启 1 关闭
            private int alarm;//被盗告警 0 推送 1 忽略
            private int electronic_fence;//电子围栏 0 开启，1 关闭
            private Fence_center fence_center;//围栏中心点
            private int fence_radius;//围栏半径

            public int getPower_warn() {
                return power_warn;
            }

            public void setPower_warn(int power_warn) {
                this.power_warn = power_warn;
            }

            public int getPower_value() {
                return power_value;
            }

            public void setPower_value(int power_value) {
                this.power_value = power_value;
            }

            public int getLeave_lock() {
                return leave_lock;
            }

            public void setLeave_lock(int leave_lock) {
                this.leave_lock = leave_lock;
            }

            public int getLeave_countdown() {
                return leave_countdown;
            }

            public void setLeave_countdown(int leave_countdown) {
                this.leave_countdown = leave_countdown;
            }

            public int getOpen_lock() {
                return open_lock;
            }

            public void setOpen_lock(int open_lock) {
                this.open_lock = open_lock;
            }

            public int getOpen_lock_distance() {
                return open_lock_distance;
            }

            public void setOpen_lock_distance(int open_lock_distance) {
                this.open_lock_distance = open_lock_distance;
            }

            public int getHitchs_up() {
                return hitchs_up;
            }

            public void setHitchs_up(int hitchs_up) {
                this.hitchs_up = hitchs_up;
            }

            public int getAlarm() {
                return alarm;
            }

            public void setAlarm(int alarm) {
                this.alarm = alarm;
            }

            public int getElectronic_fence() {
                return electronic_fence;
            }

            public void setElectronic_fence(int electronic_fence) {
                this.electronic_fence = electronic_fence;
            }

            public Fence_center getFence_center() {
                return fence_center;
            }

            public void setFence_center(Fence_center fence_center) {
                this.fence_center = fence_center;
            }

            public int getFence_radius() {
                return fence_radius;
            }

            public void setFence_radius(int fence_radius) {
                this.fence_radius = fence_radius;
            }

            public static class Fence_center  implements Serializable{
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
        public static class Msg_set  implements Serializable{
            private int steal_alarm;//防盗告警 0开启 1 关闭
            private int hitchs;//故障告警用户 0 开启 1 关闭
            private int shake;//震动告警用户 0开启 1 关闭
            private int black_out;//断电告警用户 0开启 1 关闭
            private int charge_monitor;//充电监测 0开启 1 关闭
            private int handrail;//电子围栏 0 开启 1 关闭

            public int getSteal_alarm() {
                return steal_alarm;
            }

            public void setSteal_alarm(int steal_alarm) {
                this.steal_alarm = steal_alarm;
            }

            public int getHitchs() {
                return hitchs;
            }

            public void setHitchs(int hitchs) {
                this.hitchs = hitchs;
            }

            public int getShake() {
                return shake;
            }

            public void setShake(int shake) {
                this.shake = shake;
            }

            public int getBlack_out() {
                return black_out;
            }

            public void setBlack_out(int black_out) {
                this.black_out = black_out;
            }

            public int getCharge_monitor() {
                return charge_monitor;
            }

            public void setCharge_monitor(int charge_monitor) {
                this.charge_monitor = charge_monitor;
            }

            public int getHandrail() {
                return handrail;
            }

            public void setHandrail(int handrail) {
                this.handrail = handrail;
            }
        }
    }
}
