package com.studio.tensor.ldm.pojo;

public class RoleInfo {
    private Integer id;

    private String roleName;

    private String des;

    private Long price1;

    private Long price2;

    private Long price3;

    private Long price5;

    private Byte userVisible;

    private String isDefaultRole;

    private Double length;

    private Integer num;

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

    public Long getPrice1() {
        return price1;
    }

    public void setPrice1(Long price1) {
        this.price1 = price1;
    }

    public Long getPrice2() {
        return price2;
    }

    public void setPrice2(Long price2) {
        this.price2 = price2;
    }

    public Long getPrice3() {
        return price3;
    }

    public void setPrice3(Long price3) {
        this.price3 = price3;
    }

    public Long getPrice5() {
        return price5;
    }

    public void setPrice5(Long price5) {
        this.price5 = price5;
    }

    public Byte getUserVisible() {
        return userVisible;
    }

    public void setUserVisible(Byte userVisible) {
        this.userVisible = userVisible;
    }

    public String getIsDefaultRole() {
        return isDefaultRole;
    }

    public void setIsDefaultRole(String isDefaultRole) {
        this.isDefaultRole = isDefaultRole == null ? null : isDefaultRole.trim();
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}