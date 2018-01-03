package com.studio.tensor.ldm.bean;

public class DrawPicBean
{
	public PointBean[] point;
	public PolylineBean[] polyline;
	public PolygonBean[] polygon;
	public LabelBean[] label;
	
	public static class LocationBean
	{
		public Double lon;
		public Double lat;
	}
	
	public static class PointBean
	{
		public String pointIconUrl;
		public LocationBean coor;
	}
	
	public static class PolylineBean
	{
		public String color;
		public LocationBean[] coor;
	}
	
	public static class PolygonBean
	{
		public String color;
		public LocationBean[] coor;
	}
	
	public static class LabelBean
	{
		public String color;
		public LocationBean coor;
		public String text;
	}
}
