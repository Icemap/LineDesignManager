package com.studio.tensor.ldm.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.studio.tensor.ldm.bean.LatLngInfo;
import com.studio.tensor.ldm.bean.PolylineBean.PointBean;
import com.studio.tensor.ldm.digging.CoordinateInfo;
import com.studio.tensor.ldm.digging.PolygonInfo;
import com.studio.tensor.ldm.utils.AnglelUtils;
import com.studio.tensor.ldm.utils.AutoSetUtils;
import com.studio.tensor.ldm.utils.CoodUtils;

@Controller
@RequestMapping("/calc")
public class CalculatorController
{
	@ResponseBody
	@RequestMapping("/angel")
	public Double calcuAngel(Double cLon, Double cLat,
			Double sLon, Double sLat, Double eLon, Double eLat)
	{
		return AnglelUtils.getAngle(cLon, cLat, sLon, sLat, eLon, eLat);
	}
	
	@ResponseBody
	@RequestMapping("/mercatorToWgs84")
	public List<LatLngInfo> mercatorToWgs84(String jsonCoodList)
	{
		List<LatLngInfo> coodList = new Gson().fromJson(
				jsonCoodList, new TypeToken<List<LatLngInfo>>(){}.getType());
		List<LatLngInfo> result = new ArrayList<>();
		
		for(LatLngInfo cood : coodList)
			result.add(CoodUtils.mercatorToLonLat(
					cood.getLongitude(), cood.getLatitude()));
		return result;
	}

	@ResponseBody
	@RequestMapping("/wgs84ToMercator")
	public List<LatLngInfo> wgs84ToMercator(String jsonCoodList)
	{
		List<LatLngInfo> coodList = new Gson().fromJson(
				jsonCoodList, new TypeToken<List<LatLngInfo>>(){}.getType());
		List<LatLngInfo> result = new ArrayList<>();
		
		for(LatLngInfo cood : coodList)
			result.add(CoodUtils.lonLatToMercator(
					cood.getLongitude(), cood.getLatitude()));
		return result;
	}

	@ResponseBody
	@RequestMapping("/mercatorToGcj02")
	public List<LatLngInfo> mercatorToGcj02(String jsonCoodList)
	{
		List<LatLngInfo> coodList = new Gson().fromJson(
				jsonCoodList, new TypeToken<List<LatLngInfo>>(){}.getType());
		List<LatLngInfo> result = new ArrayList<>();
		
		for(LatLngInfo cood : coodList)
			result.add(CoodUtils.mercatorToGcj(
					cood.getLongitude(), cood.getLatitude()));
		
		return result;
	}

	@ResponseBody
	@RequestMapping("/gcj02ToMercator")
	public List<LatLngInfo> gcj02ToMercator(String jsonCoodList)
	{
		List<LatLngInfo> coodList = new Gson().fromJson(
				jsonCoodList, new TypeToken<List<LatLngInfo>>(){}.getType());
		List<LatLngInfo> result = new ArrayList<>();
		
		for(LatLngInfo cood : coodList)
			result.add(CoodUtils.gcjToMercator(
					cood.getLongitude(), cood.getLatitude()));
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/wgs84ToGcj02")
	public List<LatLngInfo> wgs84ToGcj02(String jsonCoodList)
	{
		List<LatLngInfo> coodList = new Gson().fromJson(
				jsonCoodList, new TypeToken<List<LatLngInfo>>(){}.getType());
		List<LatLngInfo> result = new ArrayList<>();
		
		for(LatLngInfo cood : coodList)
		{
			LatLngInfo latlng = CoodUtils.lonLatToMercator(cood.getLongitude(), cood.getLatitude());
			result.add(CoodUtils.mercatorToGcj(
					latlng.getLongitude(), latlng.getLatitude()));
		}
		
		return result;
	}

	@ResponseBody
	@RequestMapping("/gcj02ToWgs84")
	public List<LatLngInfo> gcj02ToWgs84(String jsonCoodList)
	{
		List<LatLngInfo> coodList = new Gson().fromJson(
				jsonCoodList, new TypeToken<List<LatLngInfo>>(){}.getType());
		List<LatLngInfo> result = new ArrayList<>();
		
		for(LatLngInfo cood : coodList)
		{
			LatLngInfo latlng = CoodUtils.gcjToMercator(cood.getLongitude(), cood.getLatitude());
			result.add(CoodUtils.mercatorToLonLat(
					latlng.getLongitude(), latlng.getLatitude()));
		}
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/digging/polygon")
	public PolygonInfo polygonDigging(String sTopPointList,
			Boolean isCCW, Double slopeCoefficient, Double depth)
	{
		CoordinateInfo[] topPointList = 
				new Gson().fromJson(sTopPointList, CoordinateInfo[].class);
		Double dis = slopeCoefficient * depth;
		return new PolygonInfo(topPointList, isCCW, dis, depth);
	}
	
	@ResponseBody
	@RequestMapping("/autoTower/avgLength")
	public List<LatLngInfo> getAutoTowerByAvgLength(
			String jsonCoodList, Double avgLength)
	{
		List<LatLngInfo> coodList = new Gson().fromJson(
				jsonCoodList, new TypeToken<List<LatLngInfo>>(){}.getType());
		List<PointBean> pointList = new ArrayList<>();
		for(LatLngInfo cood : coodList)
		{
			LatLngInfo covPoint = CoodUtils.lonLatToMercator(
					cood.getLongitude(), cood.getLatitude());
			pointList.add(new PointBean(covPoint.getLongitude()
					, covPoint.getLatitude()));
		}
		
		List<PointBean> resultMoc = new ArrayList<>();
		for(int i = 0;i < pointList.size() - 1;i++)
		{
			resultMoc.addAll(AutoSetUtils.getStrightLineAuto(pointList.get(i),
					pointList.get(i + 1), avgLength));
		}
		
		List<LatLngInfo> result = new ArrayList<>();
		for(PointBean resultMocItem : resultMoc)
		{
			result.add(CoodUtils.mercatorToLonLat(resultMocItem.x, resultMocItem.y));
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/autoTower/towerNum")
	public List<LatLngInfo> getAutoTowerByAvgLength(
			String jsonCoodList, Integer towerNum)
	{
		
		List<LatLngInfo> coodList = new Gson().fromJson(
				jsonCoodList, new TypeToken<List<LatLngInfo>>(){}.getType());
		List<PointBean> pointList = new ArrayList<>();
		for(LatLngInfo cood : coodList)
		{
			LatLngInfo covPoint = CoodUtils.lonLatToMercator(
					cood.getLongitude(), cood.getLatitude());
			pointList.add(new PointBean(covPoint.getLongitude()
					, covPoint.getLatitude()));
		}
		
		Double avgLength = AutoSetUtils.getTowerLength(pointList, towerNum);
		
		List<PointBean> resultMoc = new ArrayList<>();
		for(int i = 0;i < pointList.size() - 1;i++)
		{
			resultMoc.addAll(AutoSetUtils.getStrightLineAuto(pointList.get(i),
					pointList.get(i + 1), avgLength));
		}
		
		List<LatLngInfo> result = new ArrayList<>();
		for(PointBean resultMocItem : resultMoc)
		{
			result.add(CoodUtils.mercatorToLonLat(resultMocItem.x, resultMocItem.y));
		}
		return result;
	}
}
