package com.studio.tensor.ldm.digging;

import java.util.ArrayList;
import java.util.List;

public class PolygonInfo
{
	private Integer errorCode;
	private String message;
	private Boolean[] pointReverse;
	private CoordinateInfo[] topPointList;
	private CoordinateInfo[] bottomPointList;
	private LineMethodInfo[] topLineList;
	private List<LineMethodInfo> bottomLineList;
	private Double volume;
	
	public PolygonInfo(CoordinateInfo[] topPointList, Boolean isCCW,
			Double dis, Double depth)
	{
		this(topPointList, isCCW, dis);
		
		if(errorCode == 0)
			this.volume = PolygonAlgorithm.getVolume(this.topPointList,
				this.bottomPointList, depth);
	}
	
	/**
	 * 0 : Success
	 * 1 : Input Point Less than 3
	 * 2 : All point up the bottom
	 */
	public PolygonInfo(CoordinateInfo[] topPointList, Boolean isCCW,
			Double distance)
	{
		//不是逆时针的话反转
		if(!isCCW)
			PolygonAlgorithm.reversePoints(topPointList);
				
		//添加点集顺逆属性
		topPointList = PolygonAlgorithm.setPointsIsCCWProp(topPointList);
		if(topPointList == null)
		{
			errorCode = 1;
			this.message = "输入点不足3个，生成错误。";
			return;
		}
		
		this.topPointList = topPointList;
		//格式化数据完成，所有的数据都是逆时针且大于等于三个点
		
		//生成直线,并规定方向
		topLineList = new LineMethodInfo[topPointList.length];
		for(int i = 0; i < topPointList.length - 1;i++)
		{
			topLineList[i] = new LineMethodInfo(
					topPointList[i], topPointList[i + 1]);
		}
		topLineList[topLineList.length - 1] = new LineMethodInfo(
				topPointList[topPointList.length - 1], topPointList[0]);
		
		//直线推向内部distance个单位
		bottomLineList = new ArrayList<>();
		for(int i = 0;i < topLineList.length ;i++)
		{
			bottomLineList.add(topLineList[i].clone());
			bottomLineList.get(i).moveToSelfAxesLeft(distance);
		}
		
		cul();
	}
	
	//递归计算，直到生成完成
	private void cul()
	{
		//内部直线生成交点
		bottomPointList = new CoordinateInfo[bottomLineList.size()];
		bottomPointList[0] = bottomLineList.get(0)
				.getCrossPoint(bottomLineList.get(bottomLineList.size() - 1));
		for(int i = 1 ;i < bottomLineList.size(); i++)
		{
			bottomPointList[i] = bottomLineList.get(i)
					.getCrossPoint(bottomLineList.get(i - 1));
		}
		
		//添加点的顺逆属性
		bottomPointList = PolygonAlgorithm
				.setPointsIsCCWProp(bottomPointList);
		
		//比对各点的顺逆属性是否有更改
		pointReverse = new Boolean[bottomPointList.length];
		for(int i = 0;i < bottomPointList.length;i++)
		{
			pointReverse[i] = !topPointList[i].isCCW.equals(
					bottomPointList[i].isCCW);
		}
		
		//是否无反转或全反转
		int pointReverseNum = 0;
		for(Boolean pr : pointReverse)
			if(pr) pointReverseNum++;
		
		if(pointReverseNum == 0)
		{
			errorCode = 0;
			this.message = "生成完成。";
			return;
		}
		else if(pointReverseNum == pointReverse.length)
		{
			errorCode = 2;
			this.message = "没有任意一个点可以看到垫层面。请重新输入参数。";
			return;
		}
		else
		{
			errorCode = -1;
			for(int i = 0; i < pointReverse.length;i++)
			{
				if(pointReverse[i]) 
				{
					bottomLineList.remove(i);
					cul();
					return;
				}
			}
		}
	}
	
	public CoordinateInfo[] getTopPointList()
	{
		return topPointList;
	}

	public void setTopPointList(CoordinateInfo[] topPointList)
	{
		this.topPointList = topPointList;
	}

	public CoordinateInfo[] getBottomPointList()
	{
		return bottomPointList;
	}

	public void setBottomPointList(CoordinateInfo[] bottomPointList)
	{
		this.bottomPointList = bottomPointList;
	}

	public LineMethodInfo[] getTopLineList()
	{
		return topLineList;
	}

	public void setTopLineList(LineMethodInfo[] topLineList)
	{
		this.topLineList = topLineList;
	}

	public List<LineMethodInfo> getBottomLineList()
	{
		return bottomLineList;
	}

	public void setBottomLineList(List<LineMethodInfo> bottomLineList)
	{
		this.bottomLineList = bottomLineList;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public Integer getErrorCode()
	{
		return errorCode;
	}

	public void setErrorCode(Integer errorCode)
	{
		this.errorCode = errorCode;
	}

	public Double getVolume()
	{
		return volume;
	}

	public void setVolume(Double volume)
	{
		this.volume = volume;
	}

}
