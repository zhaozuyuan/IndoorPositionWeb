package com.zzy.indoor_position.service.impl;

import com.zzy.indoor_position.service.TestService;
import com.zzy.indoor_position.sql.SQLManager;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.concurrent.*;

@Service
public class TestServiceImpl implements TestService {

    public static void main(String[] args) {

        int index = 0;
        int value = 0;
        HashMap<Integer, Integer> map = new HashMap<>();
        int i = map.put(index, value);


        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 2, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>(), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t =  new Thread(new Runnable() {
                    @Override
                    public void run() {
                        r.run();
                    }
                });
                return t;
            }
        });


        Future<String> future1 =  executor.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("hello world");
                return "hello world";
            }
        });

        Future<String> future2 = executor.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("hello world");
            }
        }, "hello world");

        Future<?> future3 = executor.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("hello world");
            }
        });

        executor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("hello world");
            }
        });
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
