package com.zzy.indoor_position.service.impl;

import com.zzy.indoor_position.service.TestService;
import com.zzy.indoor_position.sql.SQLManager;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class TestServiceImpl implements TestService {

    public static void main(String[] args) {
        int i = 0b0100 | 8;
        System.out.println(i);
    }

    @Override
    public String test() {
        String s = null;
        try {
            String sql = "select name from test where id = 1";
            PreparedStatement statement = SQLManager.getConnection().prepareStatement(sql);
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
