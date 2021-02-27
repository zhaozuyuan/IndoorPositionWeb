package com.zzy.indoor_position.controller.vo;


import org.springframework.lang.Nullable;

public class ResultVO<T> {

    private int code;
    private String msg;

    @Nullable
    private T data;

    public ResultVO(ResultEnum resultEnum) {
        this.code = resultEnum.code;
        this.msg = resultEnum.msg;
    }

    public ResultVO(int code, String msg, @Nullable T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    @Nullable
    public T getData() {
        return data;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(T data) {
        this.data = data;
    }

    public enum ResultEnum {

        SUCCESS(200, "成功"), FAILED(400, "失败"), REQUEST_ERROR(500, "请求有误");

        private String msg;
        private int code;
        ResultEnum(int code, String msg) {
            this.msg = msg;
            this.code = code;
        }
    }
}
