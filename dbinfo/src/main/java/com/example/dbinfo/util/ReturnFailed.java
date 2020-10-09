package com.example.dbinfo.util;

import lombok.Data;


public class ReturnFailed extends ReturnMessage{
    public ReturnFailed(String reason) {
        this.status = "failed";
        this.reason = reason;
    }

    public ReturnFailed() {
        this.status = "failed";
    }
}
