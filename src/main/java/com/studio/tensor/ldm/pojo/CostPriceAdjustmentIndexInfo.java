package com.studio.tensor.ldm.pojo;

public class CostPriceAdjustmentIndexInfo {
    private Integer id;

    private Integer voltageLevel;

    private Integer loopNum;

    private Double maxStandardValue;

    private String des;

    private Double adjustmentIndex;

    private String type;

    private String tag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVoltageLevel() {
        return voltageLevel;
    }

    public void setVoltageLevel(Integer voltageLevel) {
        this.voltageLevel = voltageLevel;
    }

    public Integer getLoopNum() {
        return loopNum;
    }

    public void setLoopNum(Integer loopNum) {
        this.loopNum = loopNum;
    }

    public Double getMaxStandardValue() {
        return maxStandardValue;
    }

    public void setMaxStandardValue(Double maxStandardValue) {
        this.maxStandardValue = maxStandardValue;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des == null ? null : des.trim();
    }

    public Double getAdjustmentIndex() {
        return adjustmentIndex;
    }

    public void setAdjustmentIndex(Double adjustmentIndex) {
        this.adjustmentIndex = adjustmentIndex;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag == null ? null : tag.trim();
    }
}