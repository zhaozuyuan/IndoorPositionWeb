package com.zzy.indoor_position.service;

import com.zzy.indoor_position.controller.vo.RSSITaskVO;
import org.springframework.lang.Nullable;

import java.util.List;

public interface RSSIManagerService {

    boolean saveRSSIData(RSSITaskVO data);

    @Nullable
    RSSITaskVO getRSSIData(String taskName);

    @Nullable
    List<RSSITaskVO> getAllTaskData();
}
