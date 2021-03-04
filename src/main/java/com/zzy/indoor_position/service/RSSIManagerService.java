package com.zzy.indoor_position.service;

import com.zzy.indoor_position.controller.vo.RSSITaskVO;

import java.util.List;

public interface RSSIManagerService {

    boolean saveRSSIData(RSSITaskVO data);

    RSSITaskVO getRSSIData(String taskName);

    List<RSSITaskVO> getAllTaskData();
}
