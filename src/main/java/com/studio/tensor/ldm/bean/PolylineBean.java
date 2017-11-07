package com.studio.tensor.ldm.bean;

import java.util.ArrayList;
import java.util.List;

public class PolylineBean
{
	public List<PointBean> pointList;
	public List<LineMethod> lineMethodList;
	public Double length;
	
	public PolylineBean(List<PointBean> pointList)
	{
		this.pointList = pointList;
		lineMethodList = new ArrayList<>();
		calLine();
	}
	
	public static class LineMethod
	{
		public PointBean startPoint;
		public PointBean endPoint;
		public Double length;
	}
	
	public static class PointBean
	{
		public Double x;
		public Double y;
		
		public PointBean()
		{
			
		}
		
		public PointBean(Double x, Double y)
		{
			this.x = x;
			this.y = y;
		}
	}
	
	public void calLine()
	{
		length = 0.0;
		for(int i = 0;i < pointList.size() - 1; i++)
		{
			LineMethod line = new LineMethod();
			line.startPoint = pointList.get(i);
			line.endPoint = pointList.get(i + 1);
			line.length = getLength(pointList.get(i), pointList.get(i + 1));
			lineMethodList.add(line);
			length += line.length;
		}
	}
	
	public Double getLength(PointBean point1, PointBean point2)
	{
		return Math.sqrt(Math.pow(point1.x - point2.x, 2) 
				+ Math.pow(point1.y - point2.y, 2));
	}
}
