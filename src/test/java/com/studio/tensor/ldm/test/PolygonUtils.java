package com.studio.tensor.ldm.test;

import org.junit.Test;

import com.google.gson.Gson;
import com.studio.tensor.ldm.digging.CoordinateInfo;
import com.studio.tensor.ldm.digging.PolygonInfo;

public class PolygonUtils
{
	@Test
	public void test()
	{
		//CW
		CoordinateInfo[] coorInfoList = 
		{
			new CoordinateInfo(1.0, 0.0),
			new CoordinateInfo(0.0, 1.0),
			new CoordinateInfo(-1.0, 0.0),
			new CoordinateInfo(0.0, -1.0)
		};
		System.out.println(new Gson().toJson(
				new PolygonInfo(coorInfoList, true, 2.0)));
	}
}
