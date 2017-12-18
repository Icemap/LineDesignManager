package com.studio.tensor.ldm.bean;

import com.studio.tensor.ldm.bean.PolylineBean.PointBean;

public class AutoTowerBean 
{
	public Double x;
	public Double y;
	public String pointType;
	
	public AutoTowerBean(PointBean cood, String type)
	{
		this.x = cood.x;
		this.y = cood.y;
		this.pointType = type;
	}
	
	public AutoTowerBean(Double x, Double y, String type)
	{
		this.x = x;
		this.y = y;
		this.pointType = type;
	}
	
	public AutoTowerBean(){}
	
	public Double length(AutoTowerBean p)
	{
		return Math.sqrt((p.x - this.x) * (p.x - this.x) 
				+ (p.y - this.y) * (p.y - this.y));
	}
}
