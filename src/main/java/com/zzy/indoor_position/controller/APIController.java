package com.zzy.indoor_position.controller;


import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.zzy.indoor_position.controller.vo.RSSITaskVO;
import com.zzy.indoor_position.controller.vo.ResultVO;
import com.zzy.indoor_position.service.TestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class APIController {

    //自动注入服务
    @Resource
    private TestService service;

    /**
     * GET，测试api
     */
    @GetMapping("/text")
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
        RSSITaskVO vo = null;
        try {
            vo = new Gson().fromJson(json, RSSITaskVO.class);
            System.out.println(vo);
        } catch (JsonParseException e) {
            System.out.println(e.getMessage());
        }
        if (vo == null) {
            return new Gson().toJson(new ResultVO<>(ResultVO.ResultEnum.REQUEST_ERROR));
        } else {
            return new Gson().toJson(new ResultVO<>(ResultVO.ResultEnum.SUCCESS));
        }
    }
}
