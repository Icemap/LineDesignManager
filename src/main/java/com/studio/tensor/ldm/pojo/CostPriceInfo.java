package com.studio.tensor.ldm.pojo;

public class CostPriceInfo {
    private Integer id;

    private Integer rowNum;

    private Integer sectionArea;

    private Integer loopNum;

    private String terrainName;

    private Double selfPrice;

    private Double extraPrice;

    private Double staticPrice;

    private Integer voltageLevel;

    private String tag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRowNum() {
        return rowNum;
    }

    public void setRowNum(Integer rowNum) {
        this.rowNum = rowNum;
    }

    public Integer getSectionArea() {
        return sectionArea;
    }

    public void setSectionArea(Integer sectionArea) {
        this.sectionArea = sectionArea;
    }

    public Integer getLoopNum() {
        return loopNum;
    }

    public void setLoopNum(Integer loopNum) {
        this.loopNum = loopNum;
    }

    public String getTerrainName() {
        return terrainName;
    }

    public void setTerrainName(String terrainName) {
        this.terrainName = terrainName == null ? null : terrainName.trim();
    }

    public Double getSelfPrice() {
        return selfPrice;
    }

    public void setSelfPrice(Double selfPrice) {
        this.selfPrice = selfPrice;
    }

    public Double getExtraPrice() {
        return extraPrice;
    }

    public void setExtraPrice(Double extraPrice) {
        this.extraPrice = extraPrice;
    }

    public Double getStaticPrice() {
        return staticPrice;
    }

    public void setStaticPrice(Double staticPrice) {
        this.staticPrice = staticPrice;
    }

    public Integer getVoltageLevel() {
        return voltageLevel;
    }

    public void setVoltageLevel(Integer voltageLevel) {
        this.voltageLevel = voltageLevel;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag == null ? null : tag.trim();
    }
}