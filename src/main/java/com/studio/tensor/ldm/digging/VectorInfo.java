package com.studio.tensor.ldm.digging;

public class VectorInfo
{
	public Double x;
	public Double y;
	
	public VectorInfo(Double x, Double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public VectorInfo init()
	{
		Double length = length();
		x /= length;
		y /= length;
		
		return this;
	}
	
	public Double length()
	{
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}
	
	public VectorInfo turnLeft90()
	{
		Double x1 = x * Math.cos(Math.PI / 2) - y * Math.sin(Math.PI / 2);
		Double y1 = x * Math.sin(Math.PI / 2) + y * Math.cos(Math.PI / 2);
		x = x1;
		y = y1;
		
		return this;
	}
	
	public VectorInfo multiply(Double num)
	{
		x *= num;
		y *= num;
		
		return this;
	}
}
