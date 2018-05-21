package com.wqz.test;

import java.util.ArrayList;
import java.util.List;

public class Test
{
	//实体类
	public static class PointBean
	{
		public double x;
		public double y;
		
		public PointBean(){}
		
		public PointBean(double x, double y)
		{
			this.x = x;
			this.y = y;
		}
	}
	
	//测试用例
	public static void main(String[] args)
	{
		List<PointBean> pbl = new ArrayList<>();
		
		pbl.add(new PointBean(113.42829108238222, 23.245151313249767));
		pbl.add(new PointBean(113.4283232688904, 23.242193904877386));
		pbl.add(new PointBean(113.43220710754396, 23.242193904877386));
		pbl.add(new PointBean(113.43215346336366, 23.245121739490592));
		System.out.println(getLatLonArea(pbl));
	}
	
	//最终算法最终单位m²。要转成km²，直接除1000000。
	public static double getLatLonArea(List<PointBean> pointList)
	{
		List<PointBean> pointMocList = new ArrayList<>();
		for(int i = 0;i < pointList.size();i++)
		{
			pointMocList.add(lonLatToMercator(pointList.get(i).x, pointList.get(i).y));
		}
		return getArea(pointMocList);
	}
	
	//多点面积直算
	public static double getArea(List<PointBean> pointList)
	{
		if(pointList == null || pointList.size() <= 2)
			return 0.0;
		
		Double area = 0.0;
		for(int i = 0; i < pointList.size() - 1; i++)
		{
			area += (pointList.get(i).x * pointList.get(i + 1).y - 
					pointList.get(i + 1).x * pointList.get(i).y) * 0.5;
		}
		area += (pointList.get(pointList.size() - 1).x * pointList.get(0).y - 
				pointList.get(0).x * pointList.get(pointList.size() - 1).y) * 0.5;
		
		return area;
	}
	
	//转Web墨卡托
	public static PointBean lonLatToMercator(double x, double y)
    {
        double toX = x * 20037508.34D / 180.0D;
        double toY = Math.log(Math
                .tan((90.0D + y) * 3.141592653589793D / 360.0D)) / 0.0174532925199433D;
        toY = toY * 20037508.34D / 180.0D;
        
        return new PointBean(toX, toY);
    }
}
