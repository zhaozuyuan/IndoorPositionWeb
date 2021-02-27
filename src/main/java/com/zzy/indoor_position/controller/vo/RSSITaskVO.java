package com.zzy.indoor_position.controller.vo;

import java.util.List;

/**
 * 一次任务收集的rssi数据
 */
public class RSSITaskVO {

    //任务名称，唯一
    private String task_name;

    //一个坐标下扫描的次数
    private int scan_count;

    //一个坐标下会扫描几个固定的wifi
    private int wifi_count;

    //每个坐标收集的不同的rssi数据
    private List<RSSIData> rssi_data;

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public int getScan_count() {
        return scan_count;
    }

    public void setScan_count(int scan_count) {
        this.scan_count = scan_count;
    }

    public int getWifi_count() {
        return wifi_count;
    }

    public void setWifi_count(int wifi_count) {
        this.wifi_count = wifi_count;
    }

    public List<RSSIData> getRssi_data() {
        return rssi_data;
    }

    public void setRssi_data(List<RSSIData> rssi_data) {
        this.rssi_data = rssi_data;
    }

    public static class RSSIData {
        private String wifi_ssid;
        private String wifi_bassid;
        private int x;
        private int y;
        private int level;

        public String getWifi_ssid() {
            return wifi_ssid;
        }

        public void setWifi_ssid(String wifi_ssid) {
            this.wifi_ssid = wifi_ssid;
        }

        public String getWifi_bassid() {
            return wifi_bassid;
        }

        public void setWifi_bassid(String wifi_bassid) {
            this.wifi_bassid = wifi_bassid;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }
    }

    @Override
    public String toString() {
        return "RSSITaskVO{" +
                "task_name='" + task_name + '\'' +
                ", scan_count=" + scan_count +
                ", wifi_count=" + wifi_count +
                ", rssi_data=" + rssi_data +
                '}';
    }
}