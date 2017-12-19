package com.studio.tensor.ldm.pojo;

public class BendingCoefficientInfo {
	
	public static BendingCoefficientInfo getDefault(Integer userId)
	{
		BendingCoefficientInfo info = new BendingCoefficientInfo();
		
		info.userId = userId;
		info.ground = 1.075;
		info.river = 1.15;
		info.swamp = 1.15;
		info.hills = 1.2;
		info.mountain = 1.4;
		info.higherMountain = 1.7;
		info.highestMountain = 1.7;
		info.desert = 1.4;
		
		return info;
	}
	
    private Integer userId;

    private Double ground;

    private Double river;

    private Double swamp;

    private Double hills;

    private Double mountain;

    private Double higherMountain;

    private Double highestMountain;

    private Double desert;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Double getGround() {
        return ground;
    }

    public void setGround(Double ground) {
        this.ground = ground;
    }

    public Double getRiver() {
        return river;
    }

    public void setRiver(Double river) {
        this.river = river;
    }

    public Double getSwamp() {
        return swamp;
    }

    public void setSwamp(Double swamp) {
        this.swamp = swamp;
    }

    public Double getHills() {
        return hills;
    }

    public void setHills(Double hills) {
        this.hills = hills;
    }

    public Double getMountain() {
        return mountain;
    }

    public void setMountain(Double mountain) {
        this.mountain = mountain;
    }

    public Double getHigherMountain() {
        return higherMountain;
    }

    public void setHigherMountain(Double higherMountain) {
        this.higherMountain = higherMountain;
    }

    public Double getHighestMountain() {
        return highestMountain;
    }

    public void setHighestMountain(Double highestMountain) {
        this.highestMountain = highestMountain;
    }

    public Double getDesert() {
        return desert;
    }

    public void setDesert(Double desert) {
        this.desert = desert;
    }
}