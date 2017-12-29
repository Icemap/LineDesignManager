package com.studio.tensor.ldm.utils;

import java.util.ArrayList;
import java.util.List;

import com.studio.tensor.ldm.bean.DouglasPointBean;
import com.studio.tensor.ldm.bean.LatLngInfo;
import com.studio.tensor.ldm.bean.PolylineBean.PointBean;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

public class CalcUtils
{
	/**
	 * 存储采样点数据的链表
	 */
	private List<DouglasPointBean> points = new ArrayList<DouglasPointBean>();

	/**
	 * 控制数据压缩精度的极差
	 */
	private double D = 1;

	private WKTReader reader;

	/**
	 * 构造Geometry
	 * 
	 * @param str
	 * @return
	 */
	public Geometry buildGeo(String str)
	{
		try
		{
			if (reader == null)
			{
				reader = new WKTReader();
			}
			return reader.read(str);
		}
		catch (ParseException e)
		{
			throw new RuntimeException("buildGeometry Error", e);
		}
	}

	/**
	 * 读取采样点
	 */
	public void setPoint(List<PointBean> pointList, Double level)
	{
		for (int i = 0; i < pointList.size(); i++)
		{
			DouglasPointBean p = new DouglasPointBean(pointList.get(i).x, pointList.get(i).y, i);
			points.add(p);
		}
	}

	public void initCompress()
	{
		compress(points.get(0), points.get(points.size() - 1));
	}
	
	public List<LatLngInfo> getResult()
	{
		List<LatLngInfo> result = new ArrayList<>();
		for(DouglasPointBean dau : points)
		{
			result.add(CoodUtils.mercatorToLonLat(dau.x, dau.y));
		}
		return result;
	}
	
	/**
	 * 对矢量曲线进行压缩
	 * 
	 * @param from
	 *            曲线的起始点
	 * @param to
	 *            曲线的终止点
	 */
	private void compress(DouglasPointBean from, DouglasPointBean to)
	{
		/**
		 * 压缩算法的开关量
		 */
		boolean switchvalue = false;

		/**
		 * 由起始点和终止点构成的直线方程一般式的系数
		 */
		double A = (from.y - to.y) / Math.sqrt(Math.pow((from.y - to.y), 2)
				+ Math.pow((from.x - to.x), 2));

		/**
		 * 由起始点和终止点构成的直线方程一般式的系数
		 */
		double B = (to.x - from.x)
				/ Math.sqrt(Math.pow((from.y - to.y), 2) + Math.pow((from.x - to.x), 2));

		/**
		 * 由起始点和终止点构成的直线方程一般式的系数
		 */
		double C = (from.x * to.y - to.x * from.y)
				/ Math.sqrt(Math.pow((from.y - to.y), 2) + Math.pow((from.x - to.x), 2));

		double d = 0;
		double dmax = 0;
		int m = points.indexOf(from);
		int n = points.indexOf(to);
		if (n == m + 1)
			return;
		DouglasPointBean middle = null;
		List<Double> distance = new ArrayList<Double>();
		for (int i = m + 1; i < n; i++)
		{
			d = Math.abs(A * (points.get(i).x) + B * (points.get(i).y) + C)
					/ Math.sqrt(Math.pow(A, 2) + Math.pow(B, 2));
			distance.add(d);
		}
		dmax = distance.get(0);
		for (int j = 1; j < distance.size(); j++)
		{
			if (distance.get(j) > dmax)
				dmax = distance.get(j);
		}
		if (dmax > D)
			switchvalue = true;
		else
			switchvalue = false;
		if (!switchvalue)
		{
			// 删除Points(m,n)内的坐标
			for (int i = m + 1; i < n; i++)
			{
				points.get(i).index = -1;
			}

		}
		else
		{
			for (int i = m + 1; i < n; i++)
			{
				if ((Math.abs(A * (points.get(i).x) + B * (points.get(i).y) + C)
						/ Math.sqrt(Math.pow(A, 2) + Math.pow(B, 2)) == dmax))
					middle = points.get(i);
			}
			compress(from, middle);
			compress(middle, to);
		}
	}
}
