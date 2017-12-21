package com.studio.tensor.ldm.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.studio.tensor.ldm.bean.AutoTowerBean;
import com.studio.tensor.ldm.bean.AutoTowerBeanWithLength;
import com.studio.tensor.ldm.bean.GeoCodeResultBean;
import com.studio.tensor.ldm.bean.LatLngInfo;
import com.studio.tensor.ldm.bean.MatStaBean;
import com.studio.tensor.ldm.bean.PolylineBean.PointBean;
import com.studio.tensor.ldm.digging.CoordinateInfo;
import com.studio.tensor.ldm.digging.PolygonInfo;
import com.studio.tensor.ldm.utils.AMapPointsUtils;
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
	public List<AutoTowerBeanWithLength> getAutoTowerByAvgLength(
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
		
		List<AutoTowerBean> resultMoc = new ArrayList<>();
		for(int i = 0;i < pointList.size() - 1;i++)
		{
			List<PointBean> pCoodList = AutoSetUtils.getStrightLineAuto(pointList.get(i),
					pointList.get(i + 1), avgLength);

			resultMoc.add(new AutoTowerBean(pointList.get(i), i == 0 ? "start" : "turn"));
			for(PointBean pCood : pCoodList)
			{
				resultMoc.add(new AutoTowerBean(pCood,"straight"));
			}
		}
		resultMoc.add(new AutoTowerBean(pointList.get(pointList.size() - 1), "end"));
		
		List<AutoTowerBean> result = new ArrayList<>();
		for(AutoTowerBean resultMocItem : resultMoc)
		{
			LatLngInfo latLngInfo = CoodUtils.mercatorToLonLat(resultMocItem.x, resultMocItem.y);
			result.add(new AutoTowerBean(latLngInfo.getLongitude(), latLngInfo.getLatitude(),resultMocItem.pointType));
		}
		return AutoTowerBeanWithLength.setLength(result);
	}
	
	@ResponseBody
	@RequestMapping("/autoTower/towerNum")
	public List<AutoTowerBeanWithLength> getAutoTowerByAvgLength(
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
		
		List<AutoTowerBean> resultMoc = new ArrayList<>();
		for(int i = 0;i < pointList.size() - 1;i++)
		{
			List<PointBean> pCoodList = AutoSetUtils.getStrightLineAuto(pointList.get(i),
					pointList.get(i + 1), avgLength);

			resultMoc.add(new AutoTowerBean(pointList.get(i), i == 0 ? "start" : "turn"));
			for(PointBean pCood : pCoodList)
			{
				resultMoc.add(new AutoTowerBean(pCood,"straight"));
			}
		}
		resultMoc.add(new AutoTowerBean(pointList.get(pointList.size() - 1), "end"));
		
		List<AutoTowerBean> result = new ArrayList<>();
		for(AutoTowerBean resultMocItem : resultMoc)
		{
			LatLngInfo latLngInfo = CoodUtils.mercatorToLonLat(resultMocItem.x, resultMocItem.y);
			result.add(new AutoTowerBean(latLngInfo.getLongitude(), latLngInfo.getLatitude(),resultMocItem.pointType));
		}
		return AutoTowerBeanWithLength.setLength(result);
	}
	
	@ResponseBody
	@RequestMapping("/autoMatSta")
	public List<MatStaBean> getAutoMaterialStation(
			String jsonCoodList, Integer staNum)
	{
		List<LatLngInfo> coodList = new Gson().fromJson(
				jsonCoodList, new TypeToken<List<LatLngInfo>>(){}.getType());
		List<PointBean> pointList = new ArrayList<>();
		for(LatLngInfo cood : coodList)
		{
			pointList.add(new PointBean(cood.getLongitude()
					, cood.getLatitude()));
		}
		
		List<MatStaBean> midList = AutoSetUtils.getAllMidPoint(pointList, staNum);
		List<MatStaBean> resultList = AMapPointsUtils.getNearestRoadPointThread(midList);
		return resultList;
	}
	
	@ResponseBody
	@RequestMapping("/autoGeoCode")
	public GeoCodeResultBean autoGeoCode(String jsonCoodList)
	{
		List<LatLngInfo> coodList = new Gson().fromJson(
				jsonCoodList, new TypeToken<List<LatLngInfo>>(){}.getType());
		List<PointBean> pointList = new ArrayList<>();
		for(LatLngInfo cood : coodList)
		{
			LatLngInfo lli = CoodUtils.lonLatToMercator(cood.getLongitude(), cood.getLatitude());
			pointList.add(new PointBean(lli.getLongitude(), lli.getLatitude()));
		}
		
		List<LatLngInfo> llil = AutoSetUtils.getAllLengthPoint(pointList, 500.0);
		GeoCodeResultBean geoCodeResultBean = AMapPointsUtils.getPointGeoCodeThread(llil);
		return geoCodeResultBean;
	}
}
