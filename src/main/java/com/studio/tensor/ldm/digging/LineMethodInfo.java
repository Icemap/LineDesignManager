package com.studio.tensor.ldm.digging;

public class LineMethodInfo
{
	private Double k,b;
	public CoordinateInfo startPoint;
	public CoordinateInfo endPoint;
	
	public LineMethodInfo()
	{
		
	}
	
	public LineMethodInfo(CoordinateInfo startPoint, CoordinateInfo endPoint)
	{
		this.k = (startPoint.y - endPoint.y) / (startPoint.x - endPoint.x);
		this.b = startPoint.y - this.k * startPoint.x;
		this.startPoint = startPoint;
		this.endPoint = endPoint;
	}
	
	public Double getK()
	{
		return k;
	}

	public void setK(Double k)
	{
		this.k = k;
	}

	public Double getB()
	{
		return b;
	}

	public void setB(Double b)
	{
		this.b = b;
	}
	
	/**
	 * 向自有坐标系左方移动Distance个单位 
	 */
	public void moveToSelfAxesLeft(Double Distance)
	{
		Boolean isReverse = startPoint.y > endPoint.y;
		this.b += Math.sin(Math.PI / 4 - Math.atan(this.k)) 
				* Distance * (isReverse ? -1 : 1);
	}
	
	public CoordinateInfo getCrossPoint(LineMethodInfo line2)
	{
		CoordinateInfo result = new CoordinateInfo();
		result.x = (line2.getB() - this.b) / (this.k - line2.getK());
		result.y = this.k * result.x + this.b;
		return result;
	}
}
