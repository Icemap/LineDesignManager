package com.studio.tensor.ldm.digging;

public class PolygonAlgorithm
{

	
	/**
	 * --------------------算法部分--------------------
	 */
	
	/**
	 * 节点是否为逆时针
	 */
	public static Boolean isCCW(CoordinateInfo postPoint,
			CoordinateInfo currentPoint, CoordinateInfo prePoint)
	{
		//计算叉积
		Double crossProduct = (currentPoint.x - postPoint.x) * (prePoint.y - currentPoint.y)
				- (currentPoint.y - postPoint.y) * (prePoint.x - currentPoint.x);
		return crossProduct >= 0;
	}
	
	public static CoordinateInfo[] setPointsIsCCWProp(CoordinateInfo[] points)
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
				points[points.length - 1], points[0]);
		
		return points;
	}
	
	public static void reversePoints(CoordinateInfo[] points)
	{
		CoordinateInfo temp = new CoordinateInfo();
		for(int i = 0;i < points.length / 2; i++)
		{
			temp = points[i];
			points[i] = points[points.length - 1 - i];
			points[points.length - 1 - i] = temp;
		}
	}
	
}
