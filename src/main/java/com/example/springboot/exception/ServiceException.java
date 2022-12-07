package com.example.springboot.exception;

import com.example.springboot.common.ResultCode;
import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException {
    private ResultCode code;
    /**
     * 异常构造方法 在使用时方便传入错误码和信息
     */
    public ServiceException(ResultCode code, String msg) {
        super(msg);
        this.code = code;
    }

}