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

    //x或y的单位长度 cm
    private int unit_length;

    //扫描的wifi信息
    private List<WifiTag> wifi_tags;

    //每个坐标收集的不同的rssi数据
    private List<RSSIData> rssi_data;

    public RSSITaskVO(String task_name, int scan_count, int wifi_count, int unit_length, List<WifiTag> wifi_tags, List<RSSIData> rssi_data) {
        this.task_name = task_name;
        this.scan_count = scan_count;
        this.wifi_count = wifi_count;
        this.rssi_data = rssi_data;
        this.unit_length = unit_length;
        this.wifi_tags = wifi_tags;
    }

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

    public int getUnit_length() {
        return unit_length;
    }

    public void setUnit_length(int unit_length) {
        this.unit_length = unit_length;
    }

    public List<WifiTag> getWifi_tags() {
        return wifi_tags;
    }

    public void setWifi_tags(List<WifiTag> wifi_tags) {
        this.wifi_tags = wifi_tags;
    }

    public static class RSSIData {
        private String wifi_ssid;
        private String wifi_bssid;
        private int x;
        private int y;
        private List<Integer> levels;

        public RSSIData(String wifi_ssid, String wifi_bssid, int x, int y, List<Integer> levels) {
            this.wifi_ssid = wifi_ssid;
            this.wifi_bssid = wifi_bssid;
            this.x = x;
            this.y = y;
            this.levels = levels;
        }

        public String getWifi_ssid() {
            return wifi_ssid;
        }

        public void setWifi_ssid(String wifi_ssid) {
            this.wifi_ssid = wifi_ssid;
        }

        public String getWifi_bssid() {
            return wifi_bssid;
        }

        public void setWifi_bssid(String wifi_bssid) {
            this.wifi_bssid = wifi_bssid;
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

        public List<Integer> getLevels() {
            return levels;
        }

        public void setLevels(List<Integer> levels) {
            this.levels = levels;
        }
    }

    public static class WifiTag {
        private String ssid;
        private String bssid;

        public WifiTag(String ssid, String bssid) {
            this.ssid = ssid;
            this.bssid = bssid;
        }

        public String getSsid() {
            return ssid;
        }

        public void setSsid(String ssid) {
            this.ssid = ssid;
        }

        public String getBssid() {
            return bssid;
        }

        public void setBssid(String bssid) {
            this.bssid = bssid;
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
