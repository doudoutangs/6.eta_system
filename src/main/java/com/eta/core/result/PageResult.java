package com.eta.core.result;

import com.github.pagehelper.PageInfo;

import java.io.Serializable;
import java.util.List;

/**
 */
public class PageResult<T> implements Serializable {
    private static final long serialVersionUID = -3644950655568598241L;
    private int code;
    private String msg;
    private long count;
    private List<T> data;

    public PageResult() {
    }

    public PageResult(long total, List<T> rows) {
        this.count = total;
        this.data = rows;
    }

    public PageResult(PageInfo<T> pageInfo){
        this.data=pageInfo.getList();
        this.count=pageInfo.getTotal();
    }

    public PageResult(List<T> rows){
        PageInfo<T> pageInfo=new PageInfo<>(rows);
        this.data=pageInfo.getList();
        this.count=pageInfo.getTotal();
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public PageResult<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
