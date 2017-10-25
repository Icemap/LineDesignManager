package com.studio.tensor.ldm.digging;

public class PolygonInfo
{
	private CoordinateInfo[] topPointList;
	private CoordinateInfo[] bottomPointList;
	private LineMethodInfo[] topLineList;
	private LineMethodInfo[] bottomLineList;
	
	/**
	 * 计算流程：
	 * 1.得到点集
	 * 2.添加点集顺逆属性
	 */
	public PolygonInfo(CoordinateInfo[] topPointList)
	{
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

	public LineMethodInfo[] getBottomLineList()
	{
		return bottomLineList;
	}

	public void setBottomLineList(LineMethodInfo[] bottomLineList)
	{
		this.bottomLineList = bottomLineList;
	}

}
