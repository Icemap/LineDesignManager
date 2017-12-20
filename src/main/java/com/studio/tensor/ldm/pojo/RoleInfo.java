package com.studio.tensor.ldm.pojo;

public class RoleInfo {
    private Integer id;

    private String roleName;

    private String des;

    private Long price;

    private Byte userVisible;

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

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Byte getUserVisible() {
        return userVisible;
    }

    public void setUserVisible(Byte userVisible) {
        this.userVisible = userVisible;
    }
}