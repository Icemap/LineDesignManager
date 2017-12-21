package com.studio.tensor.ldm.bean;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

public class GeoCodeResultBean
{
	public Integer provinceNum;
	public Integer cityNum;
	public Integer districtNum;
	public List<ProvinceBean> provinceList;
	
	@JsonIgnore
	public List<String> areaMsgList;
	
	public GeoCodeResultBean()
	{
		provinceNum = 0;
		cityNum = 0;
		districtNum = 0;
		provinceList = new ArrayList<>();
		areaMsgList = new ArrayList<>();
	}
	
	public static class ProvinceBean
	{
		public String provinceName;
		public List<CityBean> cityList;
		
		public ProvinceBean()
		{
			provinceName = "";
			cityList = new ArrayList<>();
		}
	}
	
	public static class CityBean
	{
		public String cityName;
		public List<String> districtList;
		
		public CityBean()
		{
			cityName = "";
			districtList = new ArrayList<>();
		}
	}
}
