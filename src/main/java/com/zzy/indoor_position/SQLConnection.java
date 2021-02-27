package com.zzy.indoor_position;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConnection {

    private static Connection sConnection;

    public static void init() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://127.0.0.1:3306/indoor_position?useSSL=false&useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&serverTimezone=UTC";
        sConnection = DriverManager.getConnection(url, "root", "123456");
    }

    public static Connection getConnection() {
        return sConnection;
    }
}
