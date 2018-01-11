package com.studio.tensor.ldm.pojo;

public class ApiInfo {
    private Integer id;

    private String apiName;

    private String url;

    private Byte isCalcLength;

    private Byte isCalcNum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName == null ? null : apiName.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Byte getIsCalcLength() {
        return isCalcLength;
    }

    public void setIsCalcLength(Byte isCalcLength) {
        this.isCalcLength = isCalcLength;
    }

    public Byte getIsCalcNum() {
        return isCalcNum;
    }

    public void setIsCalcNum(Byte isCalcNum) {
        this.isCalcNum = isCalcNum;
    }
}