package com.studio.tensor.ldm.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.google.gson.Gson;
import com.studio.tensor.ldm.bean.DrawKMLBean;
import com.studio.tensor.ldm.bean.DrawKMLBean.LocationBean;
import com.studio.tensor.ldm.bean.DrawKMLBean.PointBean;
import com.studio.tensor.ldm.bean.DrawKMLBean.PolygonBean;
import com.studio.tensor.ldm.bean.DrawKMLBean.PolylineBean;

public class KMLUtils
{
	public static void writeKML(String drawKMLBeanJson,
			String docName, String filePath, String customerData)
	{
		DrawKMLBean dpb = new Gson().fromJson(drawKMLBeanJson, DrawKMLBean.class);
//		dpb = readAlt(dpb);
		Document document = DocumentHelper.createDocument();
		Element kmlRoot = document.addElement("kml");
		kmlRoot.addAttribute("xmlns", "http://www.opengis.net/kml/2.2");
		kmlRoot.addAttribute("xmlns:gx", "http://www.google.com/kml/ext/2.2");
		kmlRoot.addAttribute("xmlns:kml", "http://www.opengis.net/kml/2.2");
		kmlRoot.addAttribute("xmlns:atom", "http://www.w3.org/2005/Atom");
		
		Element rootDoc = kmlRoot.addElement("Document");
		rootDoc.addElement("name").setText(docName);
		rootDoc.addElement("open").setText("1");
		if(!customerData.equals("")) rootDoc.addElement("customerData").setText(customerData);
		
		for(PolygonBean polygon : dpb.polygon)
		{
			Element placeMark = rootDoc.addElement("Placemark");
			placeMark.addElement("name").setText(polygon.name);
			
			Element style = placeMark.addElement("Style");
			Element lineStyle = style.addElement("LineStyle");
			lineStyle.addElement("color").setText(normalColor2KMLColor(polygon.color));
			lineStyle.addElement("width").setText("3");
			Element polyStyle = style.addElement("PolyStyle");
			polyStyle.addElement("color").setText(normalColor2TransKMLColor(polygon.color));
			polyStyle.addElement("fill").setText("1");
			polyStyle.addElement("outline").setText("1");
			
			Element poly = placeMark.addElement("Polygon");
			Element outerBoundaryIs = poly.addElement("outerBoundaryIs");
			Element linearRing = outerBoundaryIs.addElement("LinearRing");
			String sCoor = "";
			for(LocationBean loc : polygon.coor)
			{
//				LatLngInfo locWgs = CoodUtils.gcj_To_Gps84(loc.lat, loc.lon);
				sCoor += loc.lon + ",";
				sCoor += loc.lat + ",";
				sCoor += Math.round(loc.alt == null ? 0 : loc.alt) + " ";
			}
			linearRing.addElement("coordinates").setText(sCoor);
		}
		
		for(PolylineBean polyline : dpb.polyline)
		{
			Element placeMark = rootDoc.addElement("Placemark");
			placeMark.addElement("name").setText(polyline.name);
			Element style = placeMark.addElement("Style");
			Element lineStyle = style.addElement("LineStyle");
			lineStyle.addElement("color").setText(normalColor2KMLColor(polyline.color));
			lineStyle.addElement("width").setText("3");
			Element lineString = placeMark.addElement("LineString");
			String sCoor = "";
			for(LocationBean loc : polyline.coor)
			{
//				LatLngInfo locWgs = CoodUtils.gcj_To_Gps84(loc.lat, loc.lon);
				sCoor += loc.lon + ",";
				sCoor += loc.lat + ",";
				sCoor += Math.round(loc.alt == null ? 0 : loc.alt) + " ";
			}
			lineString.addElement("coordinates").setText(sCoor);
		}
		
		for(PointBean point : dpb.point)
		{
			Element placeMark = rootDoc.addElement("Placemark");
			placeMark.addElement("name").setText(point.name == null ? "" : point.name);
			Element style = placeMark.addElement("Style");
			
			Element iconStyle = style.addElement("IconStyle");
			iconStyle.addElement("color").setText("ff888888");
			iconStyle.addElement("scale").setText("1.0");
			Element icon = iconStyle.addElement("Icon");
			icon.addElement("href").setText(point.pointIconUrl);
			
			Element lineStyle = style.addElement("LineStyle");
			lineStyle.addElement("color").setText("ff0000ff");
			lineStyle.addElement("width").setText("3");
			
			Element pointEle = placeMark.addElement("Point");
			String sCoor = "";
			
//			LatLngInfo locWgs = CoodUtils.gcj_To_Gps84(point.coor.lat, point.coor.lon);
			sCoor += point.coor.lon + ",";
			sCoor += point.coor.lat + ",";
			sCoor += Math.round(point.coor.alt == null ? 0 : point.coor.alt) + " ";
			
			pointEle.addElement("coordinates").setText(sCoor);
		}
		
		try 
		{  
			FileOutputStream fileOutputStream= new FileOutputStream(filePath);  
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			XMLWriter xmlWriter=new XMLWriter(fileOutputStream, format);  
			
			xmlWriter.write(document);
			xmlWriter.close();
		} 
		catch (IOException e) 
		{  
		    e.printStackTrace();  
		}    
	}
	
	private static String normalColor2KMLColor(String color)
	{
		String kmlColor = "";
		color = color.toLowerCase();
		if(color.length() == 9)
		{
			color = color.substring(1);
			String a = color.substring(0,2);
			String r = color.substring(2,4);
			String g = color.substring(4,6);
			String b = color.substring(6,8);
			kmlColor = a + b + g + r;
		}
		else if(color.length() == 7)
		{
			color = color.substring(1);
			String r = color.substring(0,2);
			String g = color.substring(2,4);
			String b = color.substring(4,6);
			kmlColor = "ff" + b + g + r;
		}
		else
		{
			kmlColor = "ffffffff";
		}
		return kmlColor;
	}
	
	private static String normalColor2TransKMLColor(String color)
	{
		String kmlColor = normalColor2KMLColor(color);
		if(kmlColor.length() == 8)
			kmlColor = kmlColor.substring(2);
		return "88" + kmlColor;
	}

	@SuppressWarnings("unused")
	private static DrawKMLBean readAlt(DrawKMLBean srcKMLBean)
	{
		List<Double[][]> locReqList = getAllLoc(srcKMLBean);
		
		Map<String, String> params = new HashMap<String, String>();
		String linesJson = new Gson().toJson(locReqList);
		System.out.println(linesJson);
		params.put("linesJson", linesJson);
		params.put("f", "json");
		
		String respJson = HttpUtils.URLPost("http://120.78.205.53:6080/arcgis/rest/services/chinaDEM/ImageServer/exts/contourSOE/calcSlope"
				, params, HttpUtils.URL_PARAM_DECODECHARSET_UTF8);
		RespJsonBean respBean = new Gson().fromJson(respJson, RespJsonBean.class);
		return setAllLoc(srcKMLBean, respBean.results);
	}
	
	private static DrawKMLBean setAllLoc(DrawKMLBean srcKMLBean, LineJsonBean[] respResult)
	{
		Integer respIndex = 0;
		Integer respNum = 0;
		
		for(PolygonBean poly : srcKMLBean.polygon)
		{
			for(LocationBean loc : poly.coor)
			{
				loc.alt = respResult[respIndex].line[respNum][2];
				if(respNum.equals(0))
				{
					respNum ++;
				}
				else
				{
					respNum = 0;
					respIndex ++;
				}
			}
		}
		
		for(PolylineBean line : srcKMLBean.polyline)
		{
			for(LocationBean loc : line.coor)
			{
				loc.alt = respResult[respIndex].line[respNum][2];
				if(respNum.equals(0))
				{
					respNum ++;
				}
				else
				{
					respNum = 0;
					respIndex ++;
				}
			}
		}
		
		for(PointBean point : srcKMLBean.point)
		{
			point.coor.alt = respResult[respIndex].line[respNum][2];
			if(respNum.equals(0))
			{
				respNum ++;
			}
			else
			{
				respNum = 0;
				respIndex ++;
			}
		}
		
		return srcKMLBean;
	}
	
	private static List<Double[][]> getAllLoc(DrawKMLBean srcKMLBean)
	{
		List<LocationBean> locList = new ArrayList<>();
		for(PolygonBean poly : srcKMLBean.polygon)
			for(LocationBean loc : poly.coor)
				locList.add(loc);
		
		for(PolylineBean line : srcKMLBean.polyline)
			for(LocationBean loc : line.coor)
				locList.add(loc);
		
		for(PointBean point : srcKMLBean.point)
				locList.add(point.coor);
		
		List<Double[][]> locReqList = new ArrayList<>();
		for(int i = 0; i < locList.size(); i += 2)
		{
			Double[][] tempLoc = new Double[2][2];
			Double[] loc = new Double[2];
			loc[0] = locList.get(i).lon;
			loc[1] = locList.get(i).lat;
			
			Double[] loc2 = new Double[2];
			loc2[0] = locList.get(i + 1).lon;
			loc2[1] = locList.get(i + 1).lat;
			
			tempLoc[0] = loc;
			tempLoc[1] = loc2;
			locReqList.add(tempLoc);
			
			if(i + 2 == locList.size() - 1)
			{
				Double[][] tempLocLast = new Double[2][2];
				Double[] locLast = new Double[2];
				locLast[0] = locList.get(i + 2).lon;
				locLast[1] = locList.get(i + 2).lat;
				tempLocLast[0] = locLast;
				tempLocLast[1] = locLast;
				locReqList.add(tempLocLast);
				i++;
			}
		}
		
		return locReqList;
	}

	public static class RespJsonBean
	{
		String code;
		LineJsonBean[] results;
	}
	
	public static class LineJsonBean
	{
		Double[][] line;
		Double slope;
	}
}
