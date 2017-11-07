package com.studio.tensor.ldm.utils;

import java.util.ArrayList;
import java.util.List;

import com.studio.tensor.ldm.bean.PolylineBean;
import com.studio.tensor.ldm.bean.PolylineBean.LineMethod;
import com.studio.tensor.ldm.bean.PolylineBean.PointBean;

public class AutoSetUtils
{
	public static List<PointBean> getAutoTowerByAvgLength(PolylineBean polyLine, Double avgLength)
	{
		List<PointBean> result = new ArrayList<>();
		
		List<LineMethod> methodList = polyLine.lineMethodList;
		for(double i = avgLength; i < polyLine.length;i += avgLength)
		{
			Double seek = i;
			for(int j = 0;j < methodList.size();j ++)
			{
				if(seek < methodList.get(j).length)
				{
					result.add(getPointByLengthAndMethod(methodList.get(j), seek));
					break;
				}
				else
				{
					seek -= methodList.get(j).length;
				}
			}
		}
		
		return result;
	}
	
	public static List<PointBean> getAutoTowerByTowerNum(PolylineBean polyLine, Integer towerNum)
	{
		Double avgLength = polyLine.length / (towerNum + 1);
		List<PointBean> result = getAutoTowerByAvgLength(polyLine, avgLength);
		if(result.size() != towerNum)
			result.remove(result.size() - 1);
		return result;
	}
	
	//length should < lineMethod.length
	private static PointBean getPointByLengthAndMethod(LineMethod lineMethod, Double length)
	{
		Double _x = lineMethod.endPoint.x - lineMethod.startPoint.x;
		Double _y = lineMethod.endPoint.y - lineMethod.startPoint.y;
		Double rate = length / lineMethod.length;
		
		Double x = lineMethod.startPoint.x + _x * rate;
		Double y = lineMethod.startPoint.y + _y * rate;
		return new PointBean(x, y);
	}
}
