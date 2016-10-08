package com.hxp.model;

/**
 * Created by Administrator on 2016/9/5.
 */
public class Result {

    private boolean success;

    private  Object data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
