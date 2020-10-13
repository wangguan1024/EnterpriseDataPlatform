package com.zyv1.databaseconn.util;


public class ReturnMessage<T> {
    private String status;
    private T data;
    private String reason;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void success(T data){
        this.data = data;
        this.status = "success";
    }

    public void failed(String reason){
        this.status = "failed";
        this.reason = reason;
    }
}
