package com.studio.tensor.ldm.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.studio.tensor.ldm.utils.HttpUtils;
import com.studio.tensor.ldm.utils.IPUtils;

@Controller
@RequestMapping("/map")
public class MapController 
{
	String key = "3306e4b1597495612f004aa2938e7f0a";
	
	@ResponseBody
	@RequestMapping("/getLocation")
	public String getLocation(HttpServletRequest request)
	{
		Map<String, String> params = new HashMap<>();
		params.put("key", key);
		params.put("ip", IPUtils.getRealIP(request));
		return HttpUtils.URLGet("http://restapi.amap.com/v3/ip",
				params, HttpUtils.URL_PARAM_DECODECHARSET_UTF8);
	}
	
	@ResponseBody
	@RequestMapping("/drivePath")
	public String drivePath(Double startLon, Double startLat,
			Double endLon, Double endLat)
	{
		Map<String, String> params = new HashMap<>();
		params.put("key", key);
		params.put("origin", startLon + "," + startLat);
		params.put("destination", endLon + "," + endLat);
		return HttpUtils.URLGet("http://restapi.amap.com/v3/direction/driving",
				params, HttpUtils.URL_PARAM_DECODECHARSET_UTF8);
	}
}
