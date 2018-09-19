package com.airdrop.dto;

import com.airdrop.config.code.CodeEnum;

import java.util.HashMap;

/**
 * @author ShengGuang.Ye
 * @version V1.0
 * @Description: 页面响应dto
 * @date 2018/9/18 18:03
 */
public class Result extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;

    private static final String MESSAGE = "message";
    private static final String CODE = "code";

    public Result() {
        put(CODE, CodeEnum.CODE_200.getCode());
    }

    public static Result error() {
        return error(CodeEnum.CODE_500.getCode(), CodeEnum.CODE_500.getMessage());
    }

    public static Result error(String msg) {
        return error(CodeEnum.CODE_500.getCode(), msg);
    }

    public static Result error(int code, String msg) {
        return set(new Result(), code, msg);
    }

    public static Result ok(String msg) {
        return set(new Result(), CodeEnum.CODE_200.getCode(), msg);
    }

    public static Result ok() {
        return new Result();
    }

    private static Result set(Result r, int code, String msg) {
        r.put(CODE, code);
        r.put(MESSAGE, msg);
        return r;
    }

    @Override
    public Result put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}