package com.studio.tensor.ldm.pojo;

public class ApiInfo {
    private Integer id;

    private String apiName;

    private String url;

    private String hold;

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

    public String getHold() {
        return hold;
    }

    public void setHold(String hold) {
        this.hold = hold == null ? null : hold.trim();
    }
}