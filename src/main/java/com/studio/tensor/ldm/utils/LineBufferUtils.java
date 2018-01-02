package com.studio.tensor.ldm.utils;

import java.util.ArrayList;
import java.util.List;

import com.studio.tensor.ldm.bean.LatLngInfo;
import com.studio.tensor.ldm.bean.PolylineBean.PointBean;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Polygon;

public class LineBufferUtils
{
	public static List<LatLngInfo> getLineBuffer(List<PointBean> pointList,Double distance)
	{
		Coordinate[] coorArray = new Coordinate[pointList.size()];
		
		for(int i = 0;i < pointList.size(); i++)
			coorArray[i] = new Coordinate(pointList.get(i).x, pointList.get(i).y);
		
		GeometryFactory factory = new GeometryFactory();
		LineString line = factory.createLineString(coorArray);
		Polygon bufferPolygon = (Polygon)line.buffer(distance);
		Coordinate[] resultArray = bufferPolygon.getCoordinates();
		List<LatLngInfo> resultList = new ArrayList<LatLngInfo>();
		for(Coordinate coord : resultArray)
			resultList.add(CoodUtils.mercatorToLonLat(coord.x, coord.y));
		
		return resultList;
	}
}
