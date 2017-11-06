package com.studio.tensor.ldm.bean;

import java.util.List;

public class PolylineBean
{
	public List<PointBean> pointList;
	public List<LineMethod> lineMethodList;
	public Double length;
	
	public PolylineBean(List<PointBean> pointList)
	{
		this.pointList = pointList;
		calLine();
	}
	
	public class LineMethod
	{
		public Double k;
		public Double b;
		public Double length;
	}
	
	public class PointBean
	{
		public Double x;
		public Double y;
	}
	
	public void calLine()
	{
		length = 0.0;
		for(int i = 0;i < pointList.size() - 1; i++)
		{
			LineMethod line = new LineMethod();
			line.k = (pointList.get(i).y - pointList.get(i + 1).y)
					/ (pointList.get(i).x - pointList.get(i + 1).x);
			line.b = pointList.get(i).y - line.k * pointList.get(i).x;
			lineMethodList.add(line);
			length += getLength(pointList.get(i), pointList.get(i + 1));
		}
	}
	
	public Double getLength(PointBean point1, PointBean point2)
	{
		return Math.sqrt(Math.pow(point1.x - point2.x, 2) 
				+ Math.pow(point1.y - point2.y, 2));
	}
}
