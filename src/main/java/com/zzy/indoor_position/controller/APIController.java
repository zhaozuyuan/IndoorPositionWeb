package com.zzy.indoor_position.controller;


import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.zzy.indoor_position.controller.vo.RSSITaskVO;
import com.zzy.indoor_position.controller.vo.ResultVO;
import com.zzy.indoor_position.service.RSSIManagerService;
import com.zzy.indoor_position.service.TestService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class APIController {

    //自动注入服务
    @Resource
    private TestService service;

    @Resource
    private RSSIManagerService managerService;

    /**
     * GET，测试api
     */
    @GetMapping("/hello")
    public String text() {
        return service.test();
    }

    /**
     * POST
     * @param json 一次任务收集的rssi数据
     * @return 结果
     */
    @PostMapping("/pushRSSIData")
    public String pushRSSIData(@RequestBody String json) {
        System.out.println(json);
        RSSITaskVO vo = null;
        try {
            vo = new Gson().fromJson(json, RSSITaskVO.class);
            System.out.println(vo);
        } catch (JsonParseException e) {
            System.out.println(e.getMessage());
        }
        if (vo == null) {
            return new Gson().toJson(new ResultVO<>(ResultVO.ResultEnum.JSON_ERROR));
        } else {
            if (managerService.saveRSSIData(vo)) {
                return new Gson().toJson(new ResultVO<>(ResultVO.ResultEnum.SUCCESS));
            } else {
                return new Gson().toJson(new ResultVO<>(ResultVO.ResultEnum.FAILED));
            }
        }
    }

    /**
     * GET
     * @return 一次任务的rssi数据
     */
    @GetMapping("/rssiData")
    public String getRSSIData(@RequestParam("taskName") String taskName) {
        if (taskName == null || taskName.isEmpty()) {
            return new Gson().toJson(new ResultVO<>(ResultVO.ResultEnum.PARAMS_ERROR));
        } else {
            RSSITaskVO rssiTaskVO = managerService.getRSSIData(taskName);
            if (rssiTaskVO == null) {
                return new Gson().toJson(new ResultVO<>(ResultVO.ResultEnum.SUCCESS.getCode(), "未查询到数据", null));
            }
            ResultVO<RSSITaskVO> resultVO = new ResultVO<>(ResultVO.ResultEnum.SUCCESS);
            resultVO.setData(rssiTaskVO);
            return new Gson().toJson(resultVO);
        }
    }

    /**
     * GET
     * @return 所有任务的信息
     */
    @GetMapping("/allTaskData")
    public String getAllTaskData() {
        List<RSSITaskVO> data = managerService.getAllTaskData();
        if (data == null) {
            return new Gson().toJson(new ResultVO<>(ResultVO.ResultEnum.FAILED));
        } else {
            ResultVO<List<RSSITaskVO>> listResultVO = new ResultVO<>(ResultVO.ResultEnum.SUCCESS);
            listResultVO.setData(data);
            return new Gson().toJson(listResultVO);
        }
    }
}
