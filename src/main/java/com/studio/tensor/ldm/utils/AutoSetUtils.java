package com.studio.tensor.ldm.utils;

import java.util.ArrayList;
import java.util.List;

import com.studio.tensor.ldm.bean.MatStaBean;
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
	
	//New Algo
	public static List<PointBean> getStrightLineAuto(PointBean startPoint,
			PointBean endPoint, Double avgLength)
	{
		List<PointBean> result = new ArrayList<>();
		Double allLength = getLength(startPoint, endPoint);
		Integer realNum = (int)(allLength / avgLength);
		
		Double _x = endPoint.x - startPoint.x;
		Double _y = endPoint.y - startPoint.y;
		
		for(int i = 0;i < realNum - 1;i++)
		{
			Double xFeek = _x * (i + 1) / realNum;
			Double yFeek = _y * (i + 1) / realNum;
			
			result.add(new PointBean(startPoint.x + xFeek, 
					startPoint.y + yFeek));
		}
		
		return result;
	}
	
	public static Double getLength(PointBean point1, PointBean point2)
	{
		return Math.sqrt(Math.pow(point1.x - point2.x, 2) 
				+ Math.pow(point1.y - point2.y, 2));
	}
	
	public static Integer getRealTowerNum(List<PointBean> pbl, Double avgLength)
	{
		Integer num = 0;
		for(int i = 0; i < pbl.size() - 1;i++)
			num += (int)(getLength(pbl.get(i), pbl.get(i + 1)) / avgLength - 1);
		return num;
	}
	
	public static Double getTowerLength(List<PointBean> pointList, Integer towerNum)
	{
		Double bigLength = 50.0;
		Double smallLength = 1.0;
		Integer bigLengthTowerNum = AutoSetUtils.getRealTowerNum(pointList, bigLength);
		Integer smallLengthTowerNum = AutoSetUtils.getRealTowerNum(pointList, smallLength);
		
		while(bigLengthTowerNum != towerNum && smallLengthTowerNum != towerNum)
		{
			if(bigLengthTowerNum > towerNum && smallLengthTowerNum > towerNum)
			{
				smallLength = bigLength;
				bigLength *= 2;
			}
			else if(bigLengthTowerNum < towerNum && smallLengthTowerNum > towerNum)
			{
				Double middleLength = (bigLength + smallLength) / 2;
				Integer middleLengthTowerNum = AutoSetUtils.getRealTowerNum(pointList, middleLength);
				if(middleLengthTowerNum >= towerNum)
				{
					smallLength = middleLength;
				}
				else if(middleLengthTowerNum < towerNum)
				{
					bigLength = middleLength;
				}
			}
			smallLengthTowerNum = AutoSetUtils.getRealTowerNum(pointList, smallLength);
			bigLengthTowerNum = AutoSetUtils.getRealTowerNum(pointList, bigLength);
		}
		
		return smallLengthTowerNum == towerNum ? smallLength : bigLength;
	}
	
	public static Double getAllLength(List<PointBean> pointList)
	{
		Double length = 0.0;
		for(int i = 1;i < pointList.size();i++)
		{
			length += getLength(pointList.get(i - 1), pointList.get(i));
		}
		
		return length;
	}
	
	public static List<MatStaBean> getAllMidPoint(List<PointBean> pointList, Integer staNum)
	{
		Double allLength = AutoSetUtils.getAllLength(pointList);
		Double avgLength = allLength / staNum;
		
		List<MatStaBean> resultList = new ArrayList<>();
		for(int i = 0;i < staNum;i++)
		{
			MatStaBean msb = new MatStaBean();
			msb.setLocation(getPointByLength(pointList, i * avgLength + avgLength / 2));
			msb.setIndex(i);
			msb.setStartLength(avgLength * i);
			msb.setEndLength(avgLength * i + avgLength);
			resultList.add(msb);
		}
		return resultList;
	}
	
	private static PointBean getPointByLength(List<PointBean> pointList, Double length)
	{
		for(int i = 1;i < pointList.size(); i++)
		{
			Double currentLength = getLength(pointList.get(i - 1), pointList.get(i));
			if(length < currentLength)
			{
				Double rate = length / currentLength;
				Double _x = (pointList.get(i).x - pointList.get(i - 1).x) * rate;
				Double _y = (pointList.get(i).y - pointList.get(i - 1).y) * rate;
				return new PointBean(
						pointList.get(i - 1).x + _x,
						pointList.get(i - 1).y + _y);
			}
			else
				length -= currentLength;
		}
		return new PointBean();
	}
}
