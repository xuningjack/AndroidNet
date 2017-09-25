package com.example.jack.okhttpdemo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * 网络请求实体类
 * Created by xuning on 17/9/9.
 */
public class BaiduWeight {


    @Expose
    @SerializedName("Weight")
    private String mWeight;

    @Expose
    @SerializedName("From")
    private String mFrom;

    @Expose
    @SerializedName("To")
    private int mTo;

    public BaiduWeight() {
        super();
    }

    public BaiduWeight(String mWeight, String mFrom, int mTo) {
        this.mWeight = mWeight;
        this.mFrom = mFrom;
        this.mTo = mTo;
    }

    @Override
    public String toString() {
        return "BaiduWeight{" +
                "mWeight='" + mWeight + '\'' +
                ", mFrom='" + mFrom + '\'' +
                ", mTo=" + mTo +
                '}';
    }

    public String getmWeight() {
        return mWeight;
    }

    public void setmWeight(String mWeight) {
        this.mWeight = mWeight;
    }

    public String getmFrom() {
        return mFrom;
    }

    public void setmFrom(String mFrom) {
        this.mFrom = mFrom;
    }

    public int getmTo() {
        return mTo;
    }

    public void setmTo(int mTo) {
        this.mTo = mTo;
    }
}
