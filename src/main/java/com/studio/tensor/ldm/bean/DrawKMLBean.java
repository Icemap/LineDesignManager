package com.studio.tensor.ldm.bean;

public class DrawKMLBean
{
	public PointBean[] point;
	public PolylineBean[] polyline;
	public PolygonBean[] polygon;
	
	public static class LocationBean
	{
		public Double lon;
		public Double lat;
		public Double alt;
	}
	
	public static class PointBean
	{
		public String pointIconUrl;
		public LocationBean coor;
		public String name;
	}
	
	public static class PolylineBean
	{
		public String color;
		public LocationBean[] coor;
		public String name;
	}
	
	public static class PolygonBean
	{
		public String color;
		public LocationBean[] coor;
		public String name;
	}
}
