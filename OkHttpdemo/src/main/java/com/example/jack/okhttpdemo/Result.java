package com.example.jack.okhttpdemo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * 网络请求实体类
 * Created by xuning on 17/9/9.
 */
public class Result<T> {


    @Expose
    @SerializedName("reason")
    private String reason;

    @Expose
    @SerializedName("result")
    private T result;

    @Expose
    @SerializedName("error_code")
    private int errorCode;

    @Expose
    @SerializedName("resultcode")
    private String resultCode;


    public Result() {
        super();
    }

    public Result(String reason, T result, int errorCode) {
        this.reason = reason;
        this.result = result;
        this.errorCode = errorCode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public Result(String reason, T result, int errorCode, String resultCode) {
        this.reason = reason;
        this.result = result;
        this.errorCode = errorCode;
        this.resultCode = resultCode;
    }
}
