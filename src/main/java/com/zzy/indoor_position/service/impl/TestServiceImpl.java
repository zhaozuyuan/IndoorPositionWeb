package com.zzy.indoor_position.service.impl;

import com.zzy.indoor_position.SQLConnection;
import com.zzy.indoor_position.service.TestService;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service
public class TestServiceImpl implements TestService {
    @Override
    public String test() {
        String s = null;
        try {
            String sql = "select name from test where id = 1";
            PreparedStatement statement = SQLConnection.getConnection().prepareStatement(sql);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                s = set.getString(1);
                if (s != null) {
                    break;
                }
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return s == null ? "query failed." : "query success, value="+ s;
    }
}
