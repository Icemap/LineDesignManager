package com.studio.tensor.ldm.bean;

import java.util.ArrayList;
import java.util.List;

public class AutoTowerBeanWithLength
{
	public Double x;
	public Double y;
	public Double length;
	public String pointType;
	
	public AutoTowerBeanWithLength()
	{
		
	}
	
	public AutoTowerBeanWithLength(AutoTowerBean src, Double length)
	{
		this.x = src.x;
		this.y = src.y;
		this.pointType = src.pointType;
		this.length = length;
	}
	
	public static List<AutoTowerBeanWithLength> setLength(List<AutoTowerBean> srcList)
	{
		Double currentLengthSeek = 0.0;
		AutoTowerBean lastTurnBean = srcList.get(0);
		
		List<AutoTowerBeanWithLength> targetList = new ArrayList<>();
		for(AutoTowerBean src : srcList)
		{
			Double length = src.length(lastTurnBean) + currentLengthSeek;
			targetList.add(new AutoTowerBeanWithLength(src, length));
			
			if(src.pointType.equals("turn"))
			{
				currentLengthSeek = length;
				lastTurnBean = src;
			}
		}
		
		return targetList;
	}
	
	
}
