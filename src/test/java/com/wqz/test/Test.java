package com.wqz.test;

import com.studio.tensor.ldm.bean.LatLngInfo;
import com.studio.tensor.ldm.utils.CoodUtils;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Polygon;

public class Test
{
	public static void main(String[] args)
	{
		Coordinate[] coorArray = new Coordinate[2];
		
		LatLngInfo lli = CoodUtils.lonLatToMercator(104.0, 36.0);
		coorArray[0] = new Coordinate(lli.getLongitude(), lli.getLatitude());
		coorArray[1] = new Coordinate(lli.getLongitude(), lli.getLatitude());
		
		GeometryFactory factory = new GeometryFactory();
		LineString line = factory.createLineString(coorArray);
		
		Polygon bufferPolygon = (Polygon)line.buffer(250);
		Coordinate[] resultArray = bufferPolygon.getCoordinates();
		
		Double _x = resultArray[1].x - coorArray[1].x;
		Double _y = resultArray[1].y - coorArray[1].y;
		
		System.out.print(Math.sqrt(_x * _x + _y * _y));
	}
}
