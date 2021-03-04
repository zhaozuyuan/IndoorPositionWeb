package com.zzy.indoor_position.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLManager {

    private static Connection sConnection;

    public static void init() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://127.0.0.1:3306/indoor_position?useSSL=false&useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&serverTimezone=UTC";
        sConnection = DriverManager.getConnection(url, "root", "123456");
    }

    public static Connection getConnection() {
        return sConnection;
    }

    public static boolean queryExist(String table, String key, String value) {
        String sql = "select id from " + table + " where " + key + "=" + value;
        System.out.println("isExist: " + sql);
        try {
            ResultSet set = getConnection().prepareStatement(sql).executeQuery();
            if (set.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException troubles) {
            troubles.printStackTrace();
            return false;
        }
    }

    public static void delete(String table, String key, String value) {
        String sql = "delete from " + table + " where " + key + "=" + value;
        System.out.println("delete: " + sql);
        try {
            getConnection().prepareStatement(sql).execute();
        } catch (SQLException troubles) {
            troubles.printStackTrace();
        }
    }
}
