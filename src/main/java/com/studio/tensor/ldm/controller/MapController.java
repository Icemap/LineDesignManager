package com.studio.tensor.ldm.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.studio.tensor.ldm.bean.LatLngInfo;
import com.studio.tensor.ldm.utils.AMapPointsUtils;
import com.studio.tensor.ldm.utils.CoodUtils;
import com.studio.tensor.ldm.utils.HttpUtils;
import com.studio.tensor.ldm.utils.IPUtils;

@Controller
@RequestMapping("/map")
public class MapController 
{
	String key = AMapPointsUtils.key;
	
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
		LatLngInfo sLoc = CoodUtils.gps84_To_Gcj02(startLat, startLon);
		LatLngInfo eLoc = CoodUtils.gps84_To_Gcj02(endLat, endLon);
		
		Map<String, String> params = new HashMap<>();
		params.put("key", key);
		params.put("origin", sLoc.getLongitude() + "," + sLoc.getLatitude());
		params.put("destination", eLoc.getLongitude() + "," + eLoc.getLatitude());
		return HttpUtils.URLGet("http://restapi.amap.com/v3/direction/driving",
				params, HttpUtils.URL_PARAM_DECODECHARSET_UTF8);
	}
	
	@ResponseBody
	@RequestMapping("/inputHint")
	public String inputHint(String keywords)
	{
		Map<String, String> params = new HashMap<>();
		params.put("key", key);
		params.put("keywords", keywords);
		return HttpUtils.URLGet("http://restapi.amap.com/v3/assistant/inputtips",
				params, HttpUtils.URL_PARAM_DECODECHARSET_UTF8);
	}
}
