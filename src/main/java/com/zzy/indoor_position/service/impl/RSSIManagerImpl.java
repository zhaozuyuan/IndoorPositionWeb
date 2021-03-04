package com.zzy.indoor_position.service.impl;

import com.zzy.indoor_position.controller.vo.RSSITaskVO;
import com.zzy.indoor_position.service.RSSIManagerService;
import com.zzy.indoor_position.sql.SQLManager;
import com.zzy.indoor_position.sql.TableData;
import com.zzy.indoor_position.sql.TableTask;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class RSSIManagerImpl implements RSSIManagerService {

    private static AtomicLong atomicNameId = new AtomicLong(System.currentTimeMillis());

    @Override
    public boolean saveRSSIData(RSSITaskVO data) {
        long nameId;

        boolean isExist = false;
        String taskNameValue = "'" + data.getTask_name() + "'";
        if ((nameId = queryNameId(data.getTask_name())) > 0) {
            isExist = true;
        }
        if (isExist) {
            SQLManager.delete(TableTask.NAME, TableTask.TASK_NAME, taskNameValue);
            SQLManager.delete(TableData.NAME, TableData.TASK_NAME_ID, "" + nameId);
        } else {
            //支持并发，和减少冲突
            nameId = System.currentTimeMillis() + (data.getTask_name().hashCode() & 0b11111111 << 20);
            while (!atomicNameId.compareAndSet(atomicNameId.get(), nameId)) {
                nameId = System.currentTimeMillis();
            }
        }
        String saveToTask = "insert into " + TableTask.NAME + "("
                + TableTask.TASK_NAME + ","
                + TableTask.TASK_NAME_ID + ","
                + TableTask.WIFI_COUNT + ","
                + TableTask.SCAN_COUNT + ","
                + TableTask.UNIT_LENGTH
                + ")"
                + " values("
                + "'" + data.getTask_name() + "',"
                + nameId + ","
                + data.getWifi_count() + ","
                + data.getScan_count() + ","
                + data.getUnit_length()
                + ")";
        System.out.println("saveToTask: " + saveToTask);
        try {
            SQLManager.getConnection().prepareStatement(saveToTask).execute();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            return false;
        }

        StringBuilder saveToDataBuilder = new StringBuilder("insert into " + TableData.NAME + "("
                + TableData.TASK_NAME_ID + ","
                + TableData.X + ","
                + TableData.Y + ","
                + TableData.WIFI_SSID + ","
                + TableData.WIFI_BASSID + ","
                + TableData.LEVELS
                + ")"
                + "values");
        int size = data.getRssi_data().size();
        for (int i = 0; i < size; i++) {
            RSSITaskVO.RSSIData once = data.getRssi_data().get(i);
            StringBuilder levelStrBuilder = new StringBuilder();
            for (int l : once.getLevels()) {
                levelStrBuilder.append(l).append(",");
            }
            saveToDataBuilder.append("(")
                    .append(nameId).append(",")
                    .append(once.getX()).append(",")
                    .append(once.getY()).append(",")
                    .append("'").append(once.getWifi_ssid()).append("',")
                    .append("'").append(once.getWifi_bassid()).append("',")
                    .append("'").append(levelStrBuilder.toString()).append("'")
                    .append(")");
            if (i != size - 1) {
                saveToDataBuilder.append(",");
            }
        }
        System.out.println("saveToData: " + saveToDataBuilder.toString());
        try {
            SQLManager.getConnection().prepareStatement(saveToDataBuilder.toString()).execute();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public RSSITaskVO getRSSIData(String taskName) {
        long nameId = queryNameId(taskName);
        if (nameId <= 0) {
            return null;
        }

        int wifiCount;
        int scanCount;
        int unitLength;
        String queryTaskSql = "select * from " + TableTask.NAME + " where " + TableTask.TASK_NAME_ID + "=" + nameId;
        System.out.println("queryTaskSql: " + queryTaskSql);
        try {
            ResultSet resultSet = SQLManager.getConnection().prepareStatement(queryTaskSql).executeQuery();
            resultSet.next();
            wifiCount = resultSet.getInt(TableTask.WIFI_COUNT);
            scanCount = resultSet.getInt(TableTask.SCAN_COUNT);
            unitLength = resultSet.getInt(TableTask.UNIT_LENGTH);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            return null;
        }

        List<RSSITaskVO.RSSIData> dataList = new ArrayList<>();
        String queryDataSql = "select * from " + TableData.NAME + " where " + TableData.TASK_NAME_ID + "=" + nameId;
        System.out.println("queryDataSql: " + queryDataSql);
        try {
            ResultSet resultSet = SQLManager.getConnection().prepareStatement(queryDataSql).executeQuery();
            while (resultSet.next()) {
                int x = resultSet.getInt(TableData.X);
                int y = resultSet.getInt(TableData.Y);
                String wifiSSID = resultSet.getString(TableData.WIFI_SSID);
                String wifiBASSID = resultSet.getString(TableData.WIFI_BASSID);
                String levels = resultSet.getString(TableData.LEVELS);
                List<Integer> levelList = Arrays.stream(levels.split(","))
                        .map(Integer::valueOf).collect(Collectors.toList());
                dataList.add(new RSSITaskVO.RSSIData(wifiSSID, wifiBASSID, x, y, levelList));
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            return null;
        }
        return new RSSITaskVO(taskName, scanCount, wifiCount, unitLength, dataList);
    }

    @Override
    public List<RSSITaskVO> getAllTaskData() {
        String querySql = "select * from " + TableTask.NAME;
        List<RSSITaskVO> result = new ArrayList<>();
        try {
            ResultSet resultSet = SQLManager.getConnection().prepareStatement(querySql).executeQuery();
            while (resultSet.next()) {
                String taskName = resultSet.getString(TableTask.TASK_NAME);
                int wifiCount = resultSet.getInt(TableTask.WIFI_COUNT);
                int scanCount = resultSet.getInt(TableTask.SCAN_COUNT);
                int unitLength = resultSet.getInt(TableTask.UNIT_LENGTH);
                result.add(new RSSITaskVO(taskName, scanCount, wifiCount, unitLength, null));
            }
            return result;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    private long queryNameId(String taskName) {
        long nameId = -1L;
        String taskNameValue = "'" + taskName + "'";
        String queryNameId = "select " + TableTask.TASK_NAME_ID + " from " + TableTask.NAME
                + " where " + TableTask.TASK_NAME + "=" + taskNameValue;
        ResultSet set;
        try {
            set = SQLManager.getConnection().prepareStatement(queryNameId).executeQuery();
            while (set.next()) {
                nameId = set.getLong(TableTask.TASK_NAME_ID);
                if (nameId > 0) {
                    break;
                }
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return nameId;
    }
}
