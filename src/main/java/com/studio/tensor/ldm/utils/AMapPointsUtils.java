package com.studio.tensor.ldm.utils;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.studio.tensor.ldm.bean.MatStaBean;
import com.studio.tensor.ldm.bean.PolylineBean.PointBean;

public class AMapPointsUtils
{
	public static String key = "3306e4b1597495612f004aa2938e7f0a";
	
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
}
