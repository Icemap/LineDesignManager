package com.studio.tensor.ldm.digging;

public class CoordinateInfo
{
	public Double x;
	public Double y;
	public Boolean isCCW; 
	
	public CoordinateInfo clone()
	{
		CoordinateInfo coor = new CoordinateInfo();
		coor.x = this.x;
		coor.y = this.y;
		coor.isCCW = this.isCCW;
		return coor;
	}
	
	public CoordinateInfo()
	{
		
	}
	
	public CoordinateInfo(Double x, Double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public CoordinateInfo addVector(VectorInfo vec)
	{
		x += vec.x;
		y += vec.y;
		
		return this;
	}
}
