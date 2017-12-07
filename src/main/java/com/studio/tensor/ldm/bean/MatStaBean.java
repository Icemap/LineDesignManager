package com.studio.tensor.ldm.bean;

import com.studio.tensor.ldm.bean.PolylineBean.PointBean;

public class MatStaBean
{
	private PointBean location;
	private Double startLength;
	private Double endLength;
	private Integer index;
	
	public PointBean getLocation()
	{
		return location;
	}
	public void setLocation(PointBean location)
	{
		this.location = location;
	}
	public Double getStartLength()
	{
		return startLength;
	}
	public void setStartLength(Double startLength)
	{
		this.startLength = startLength;
	}
	public Double getEndLength()
	{
		return endLength;
	}
	public void setEndLength(Double endLength)
	{
		this.endLength = endLength;
	}
	public Integer getIndex()
	{
		return index;
	}
	public void setIndex(Integer index)
	{
		this.index = index;
	}
}
