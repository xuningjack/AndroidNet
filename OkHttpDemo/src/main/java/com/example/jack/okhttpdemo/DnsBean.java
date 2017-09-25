package com.example.jack.okhttpdemo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;




/**
 * 网络请求实体类
 * Created by xuning on 17/9/9.
 */
public class DnsBean {


    @Expose
    @SerializedName("nature")
    private String nature;
    @Expose
    @SerializedName("icp")
    private String icp;
    @Expose
    @SerializedName("indexUrl")
    private String indexUrl;
    @Expose
    @SerializedName("sitename")
    private String sitename;
    @Expose
    @SerializedName("domain")
    private String domain;
    @Expose
    @SerializedName("nowIcp")
    private String nowIcp;
    @Expose
    @SerializedName("type")
    private int mType;
    @Expose
    @SerializedName("search")
    private String mSearch;
    @Expose
    @SerializedName("checkDate")
    private String checkDate;
    @Expose
    @SerializedName("name")
    private String name;


    public DnsBean() {
        super();
    }

    public DnsBean(String nature, String icp, String indexUrl, String sitename, String domain,
                   String nowIcp, int mType, String mSearch, String checkDate, String name) {
        this.nature = nature;
        this.icp = icp;
        this.indexUrl = indexUrl;
        this.sitename = sitename;
        this.domain = domain;
        this.nowIcp = nowIcp;
        this.mType = mType;
        this.mSearch = mSearch;
        this.checkDate = checkDate;
        this.name = name;
    }

    @Override
    public String toString() {
        return "DnsBean{" +
                "nature='" + nature + '\'' +
                ", icp='" + icp + '\'' +
                ", indexUrl='" + indexUrl + '\'' +
                ", sitename='" + sitename + '\'' +
                ", domain='" + domain + '\'' +
                ", nowIcp='" + nowIcp + '\'' +
                ", mType=" + mType +
                ", mSearch='" + mSearch + '\'' +
                ", checkDate='" + checkDate + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getIcp() {
        return icp;
    }

    public void setIcp(String icp) {
        this.icp = icp;
    }

    public String getIndexUrl() {
        return indexUrl;
    }

    public void setIndexUrl(String indexUrl) {
        this.indexUrl = indexUrl;
    }

    public String getSitename() {
        return sitename;
    }

    public void setSitename(String sitename) {
        this.sitename = sitename;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getNowIcp() {
        return nowIcp;
    }

    public void setNowIcp(String nowIcp) {
        this.nowIcp = nowIcp;
    }

    public int getmType() {
        return mType;
    }

    public void setmType(int mType) {
        this.mType = mType;
    }

    public String getmSearch() {
        return mSearch;
    }

    public void setmSearch(String mSearch) {
        this.mSearch = mSearch;
    }

    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
