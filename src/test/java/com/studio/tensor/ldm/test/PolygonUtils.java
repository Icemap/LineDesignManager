package com.studio.tensor.ldm.test;

import org.junit.Test;

import com.google.gson.Gson;
import com.studio.tensor.ldm.digging.CoordinateInfo;

public class PolygonUtils
{
	
	
	/**
	 * --------------------算法部分--------------------
	 */
	
	/**
	 * 节点是否为逆时针
	 */
	public Boolean isCCW(CoordinateInfo postPoint,
			CoordinateInfo currentPoint, CoordinateInfo prePoint)
	{
		//计算叉积
		Double crossProduct = (currentPoint.x - postPoint.x) * (prePoint.y - currentPoint.y)
				- (currentPoint.y - postPoint.y) * (prePoint.x - currentPoint.x);
		return crossProduct >= 0;
	}
	
	public CoordinateInfo[] setPointsIsCCWProp(CoordinateInfo[] points)
	{
		if(points.length < 3) return null;
		
		points[0].isCCW = isCCW(points[points.length - 1],
				points[0], points[1]);
		for(int i = 1;i < points.length - 1;i++)
		{
			points[i].isCCW = isCCW(points[i - 1],
					points[i], points[i + 1]);
		}
		points[points.length - 1].isCCW = 
				isCCW(points[points.length - 2],
				points[points.length - 2], points[0]);
		
		return points;
	}
	
	@Test
	public void test()
	{
		CoordinateInfo[] coorInfoList = 
		{
			new CoordinateInfo(0.0, -1.0),
			new CoordinateInfo(0.0, 0.0),
			new CoordinateInfo(1.0, 1.0)
		};
		
		CoordinateInfo[] reverseCoorInfoList = 
		{
			new CoordinateInfo(1.0, 1.0),
			new CoordinateInfo(0.0, 0.0),
			new CoordinateInfo(0.0, -1.0)
		};
		
		System.out.println(new Gson().toJson(setPointsIsCCWProp(reverseCoorInfoList)));
	}
}
