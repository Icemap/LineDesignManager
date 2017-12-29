package com.studio.tensor.ldm.bean;

import org.codehaus.jackson.annotate.JsonIgnore;

public class DouglasPointBean
{
	public Double x;
	public Double y;
	@JsonIgnore
	public Integer index;
	
	public DouglasPointBean()
	{
		
	}
	
	public DouglasPointBean(Double x, Double y, Integer index)
	{
		this.x = x;
		this.y = y;
		this.index = index;
	}
}
