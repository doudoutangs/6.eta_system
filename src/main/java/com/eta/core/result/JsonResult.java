package com.eta.core.result;

import java.io.Serializable;

/**
 */
public class JsonResult<T> implements Serializable {
    private static final long serialVersionUID = -3644950655568598241L;

    private int state;
    private String message;
    private Integer code;
    private T data;

    public static final int SUCCESS = 0;
    public static final int ERROR = 1;

    public static final int EXCEPTION=500001;//异常错误
    public static final int OK=200;            //正确
    public static final int NO=500002;       //处理错误


    public JsonResult() {
        state = SUCCESS;
        code=OK;
        message = "";
    }

    public JsonResult(T data){
        state = SUCCESS;
        code=OK;
        this.data = data;
    }

    public JsonResult(Throwable e){
        state = ERROR;
        code=EXCEPTION;
        message = e.getMessage();
    }

    public JsonResult(int state,String message){
        code=NO;
        this.state = state;
        this.message = message;
    }

    public JsonResult(int state, Throwable e){
        this.state = state;
        this.message = e.getMessage();
        code=EXCEPTION;
    }

    public JsonResult(int state, T data){
        this.state = state;
        this.data = data;
        if(state==SUCCESS){
            code=OK;
        }else{
            code=NO;
        }
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "JsonResult [state=" + state + ", message=" + message + ", data=" + data + "]";
    }
}
