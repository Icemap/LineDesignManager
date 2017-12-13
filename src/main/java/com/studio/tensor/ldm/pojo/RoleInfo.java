package com.studio.tensor.ldm.pojo;

public class RoleInfo {
    private Integer id;

    private String roleName;

    private String des;

    private Integer frontNodeId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des == null ? null : des.trim();
    }

    public Integer getFrontNodeId() {
        return frontNodeId;
    }

    public void setFrontNodeId(Integer frontNodeId) {
        this.frontNodeId = frontNodeId;
    }
}