package com.eta.core.entity;

import java.io.Serializable;

public class ActionResult<T> implements Serializable {
    private static final long serialVersionUID = -3644950655568598241L;

    private String errcode="0";
    private String message="ok";
    private T data;

    public ActionResult() {

    }

    public ActionResult(T data){
        /*errcode = "0";
        message="ok";*/
        this.data = data;
    }

    public ActionResult(String errcode, Throwable e){
        this.errcode = errcode;
        message = e.getMessage();
    }

    public ActionResult(String errcode, String message){
        this.errcode = errcode;
        this.message = message;
    }

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
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
}
