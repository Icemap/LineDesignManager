package com.studio.tensor.ldm.pojo;

public class RoleInfo {
    private Integer id;

    private String name;

    private String des;

    private String apiJson;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des == null ? null : des.trim();
    }

    public String getApiJson() {
        return apiJson;
    }

    public void setApiJson(String apiJson) {
        this.apiJson = apiJson == null ? null : apiJson.trim();
    }
}