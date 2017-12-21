package com.studio.tensor.ldm.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.studio.tensor.ldm.bean.GeoCodeBean;
import com.studio.tensor.ldm.bean.GeoCodeResultBean;
import com.studio.tensor.ldm.bean.GeoCodeResultBean.CityBean;
import com.studio.tensor.ldm.bean.GeoCodeResultBean.ProvinceBean;
import com.studio.tensor.ldm.bean.LatLngInfo;
import com.studio.tensor.ldm.bean.MatStaBean;
import com.studio.tensor.ldm.bean.PolylineBean.PointBean;

public class AMapPointsUtils
{
	public static String key = "d8619be6ae77395055841ae5daa0e467";
	
	public static MatStaBean getNearestRoadPoint(MatStaBean p)
	{
		//统一终点：北京崇文门
		Map<String, String> params = new HashMap<>();
		params.put("key", key);
		params.put("origin", p.getLocation().x + "," + p.getLocation().y);
		params.put("destination", "116.434446,39.90816");
		params.put("extensions", "base");
		
		String urlGet = HttpUtils.URLGet("http://restapi.amap.com/v3/direction/driving",
				params, HttpUtils.URL_PARAM_DECODECHARSET_UTF8);
		JsonParser jsonParser = new JsonParser();
		JsonElement userJsonElement = jsonParser.parse(urlGet);
		JsonObject route = userJsonElement.getAsJsonObject().get("route").getAsJsonObject();
		JsonArray paths = route.getAsJsonArray("paths");
		JsonArray steps = paths.get(0).getAsJsonObject().getAsJsonArray("steps");
		String line = steps.get(0).getAsJsonObject().get("polyline").getAsString();
		
		String[] lineList = line.split(";");
		Double x = Double.parseDouble(lineList[0].split(",")[0]);
		Double y = Double.parseDouble(lineList[0].split(",")[1]);
		p.setLocation(new PointBean(x, y));
		return p;
	}
	
	public static List<MatStaBean> getNearestRoadPointThread(List<MatStaBean> srcBeanList)
	{
		List<MatStaBean> resultList = new ArrayList<MatStaBean>();
		
		// 使用线程安全的Vector
		Vector<Thread> threads = new Vector<Thread>();
		for (int i = 0; i < srcBeanList.size(); i++)
		{
			final int index = i;
			Thread iThread = new Thread(new Runnable()
			{
				public void run()
				{
					resultList.add(getNearestRoadPoint(srcBeanList.get(index)));
					System.out.println("子线程" + Thread.currentThread() + "执行完毕");
				}
			});
			threads.add(iThread);
			iThread.start();
		}

		for (Thread iThread : threads)
		{
			try
			{
				// 等待所有线程执行完毕
				iThread.join();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}

		System.out.println("主线执行。");
		matStaBeanListSort(resultList);
		return resultList;
	}
	
	private static void matStaBeanListSort(List<MatStaBean> matStaBeanList)
	{
		MatStaBean temp;
		for(int i = 0; i < matStaBeanList.size() - 1; i++)
		{
			for(int j = i + 1; j < matStaBeanList.size(); j++)
			{
				if(matStaBeanList.get(j).getIndex() < matStaBeanList.get(i).getIndex())
				{
					temp = matStaBeanList.get(j);
					matStaBeanList.set(j, matStaBeanList.get(i));
					matStaBeanList.set(i, temp);
				}
			}
		}
	}
	
	public static GeoCodeResultBean getPointGeoCodeThread(List<LatLngInfo> latLngInfos)
	{
		List<GeoCodeBean> geoCodeList = new ArrayList<>();
		
		// 使用线程安全的Vector
		Vector<Thread> threads = new Vector<Thread>();
		for (int i = 0; i < latLngInfos.size(); i++)
		{
			final int index = i;
			Thread iThread = new Thread(new Runnable()
			{
				public void run()
				{
					geoCodeList.add(getPointGeoCode(latLngInfos.get(index)));
					System.out.println("子线程" + Thread.currentThread() + "执行完毕");
				}
			});
			threads.add(iThread);
			iThread.start();
		}

		for (Thread iThread : threads)
		{
			try
			{
				// 等待所有线程执行完毕
				iThread.join();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}

		System.out.println("主线执行。");
		GeoCodeResultBean geoCodeResultBean = buildGeoCodeBeanList(geoCodeList);
		return geoCodeResultBean;
	}
	
	public static GeoCodeBean getPointGeoCode(LatLngInfo coodMer)
	{
		LatLngInfo cood = CoodUtils.mercatorToLonLat(coodMer.getLongitude(), coodMer.getLatitude());
		Map<String, String> params = new HashMap<>();
		params.put("key", key);
		params.put("location", cood.getLongitude() + "," + cood.getLatitude());
		params.put("radius", "1");
		params.put("extensions", "base");
		
		String urlGet = HttpUtils.URLGet("http://restapi.amap.com/v3/geocode/regeo",
				params, HttpUtils.URL_PARAM_DECODECHARSET_UTF8);
		JsonParser jsonParser = new JsonParser();
		JsonElement userJsonElement = jsonParser.parse(urlGet);
		JsonObject regeocode = userJsonElement.getAsJsonObject().get("regeocode").getAsJsonObject();
		JsonObject addressComponent =  regeocode.get("addressComponent").getAsJsonObject();
		
		GeoCodeBean geoCodeBean = new GeoCodeBean();
		try
		{
			geoCodeBean.province = addressComponent.get("province").getAsString();
		}
		catch (Exception e) 
		{
			geoCodeBean.province = "";
		}
		try
		{
			geoCodeBean.city = addressComponent.get("city").getAsString();
		}
		catch (Exception e) 
		{
			geoCodeBean.city = "";
		}
		try
		{
			geoCodeBean.district = addressComponent.get("district").getAsString();
		}
		catch (Exception e) 
		{
			geoCodeBean.district = "";
		}
		
		return geoCodeBean;
	}
	
	private static GeoCodeResultBean buildGeoCodeBeanList(List<GeoCodeBean> geoCodeBeanList)
	{
		GeoCodeResultBean geoCodeResultBean = new GeoCodeResultBean();
		for(GeoCodeBean geoCodeBean : geoCodeBeanList)
		{
			String areaMsg = geoCodeBean.province + geoCodeBean.city + geoCodeBean.district;
			if(geoCodeResultBean.areaMsgList.contains(areaMsg)) 
				continue;
			
			geoCodeResultBean.areaMsgList.add(areaMsg);
			
			Boolean isProvinceExist = false;
			for(ProvinceBean provinceBean : geoCodeResultBean.provinceList)
			{
				if(provinceBean.provinceName.equals(geoCodeBean.province))
				{
					Boolean isCityExist = false;
					for(CityBean cityBean : provinceBean.cityList)
					{
						if(cityBean.cityName.equals(geoCodeBean.city))
						{
							cityBean.districtList.add(geoCodeBean.district);
							geoCodeResultBean.districtNum ++;
							
							isCityExist = true;
							break;
						}
					}
					
					if(!isCityExist)
					{
						CityBean cityBean = new CityBean();
						cityBean.cityName = geoCodeBean.city;
						cityBean.districtList.add(geoCodeBean.district);
						provinceBean.cityList.add(cityBean);
						
						geoCodeResultBean.cityNum ++;
						geoCodeResultBean.districtNum ++;
					}
					
					isProvinceExist = true;
					break;
				}
			}
			
			if(!isProvinceExist)
			{
				CityBean cityBean = new CityBean();
				cityBean.cityName = geoCodeBean.city;
				cityBean.districtList.add(geoCodeBean.district);
				
				ProvinceBean provinceBean = new ProvinceBean();
				provinceBean.provinceName = geoCodeBean.province;
				provinceBean.cityList.add(cityBean);
				geoCodeResultBean.provinceList.add(provinceBean);

				geoCodeResultBean.provinceNum ++;
				geoCodeResultBean.cityNum ++;
				geoCodeResultBean.districtNum ++;
			}
		}
		
		return geoCodeResultBean;
	}
}
