package com.studio.tensor.ldm.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.studio.tensor.ldm.utils.LongWaitHttpUtils;

@Controller
@RequestMapping("/gisProxy")
public class GISController
{
	@ResponseBody
	@RequestMapping("/arcgis/rest/services/chinaDEM/ImageServer/exts/contourSOE/getContour")
	public void getContour(HttpServletResponse response,
			String xmin, String ymin, String xmax, String ymax,
			String value, String f) throws IOException
	{
		Map<String, String> params = new HashMap<>();
		params.put("xmin", xmin);
		params.put("ymin", ymin);
		params.put("xmax", xmax);
		params.put("ymax", ymax);
		params.put("value", value);
		params.put("f", f);
		
		response.getWriter().write(LongWaitHttpUtils.URLGet(
				"http://120.78.205.53:6080/arcgis/rest/services/chinaDEM/ImageServer/exts/contourSOE/getZTSJ",
				params, LongWaitHttpUtils.URL_PARAM_DECODECHARSET_UTF8));
	}
	
	@ResponseBody
	@RequestMapping("/arcgis/rest/services/ztsj/MapServer/exts/ztsj/getZTSJ")
	public void getContour(HttpServletResponse response,String type, 
			String province, String yearlevel, String f) throws IOException
	{
		Map<String, String> params = new HashMap<>();
		params.put("type", type);
		params.put("province", province);
		params.put("yearlevel", yearlevel);
		params.put("f", f);
		
		response.getWriter().write(LongWaitHttpUtils.URLGet(
				"http://120.78.205.53:6080/arcgis/rest/services/ztsj/MapServer/exts/ztsj/getZTSJ",
				params, LongWaitHttpUtils.URL_PARAM_DECODECHARSET_UTF8));
	}

	@ResponseBody
	@RequestMapping("/arcgis/rest/services/chinaDEM/ImageServer/exts/contourSOE/getSection")
	public void getSection(HttpServletResponse response,
			String lineJson, String f) throws IOException
	{
		Map<String, String> params = new HashMap<>();
		params.put("lineJson", lineJson);
		params.put("f", f);
		
		response.getWriter().write(LongWaitHttpUtils.URLGet(
				"http://120.78.205.53:6080/arcgis/rest/services/chinaDEM/ImageServer/exts/contourSOE/getSection",
				params, LongWaitHttpUtils.URL_PARAM_DECODECHARSET_UTF8));
	}

	@ResponseBody
	@RequestMapping("/arcgis/rest/services/chinaDEM/ImageServer/exts/contourSOE/getAverageSlope")
	public void getAverageSlope(HttpServletResponse response,
			String lineJson, String f) throws IOException
	{
		Map<String, String> params = new HashMap<>();
		params.put("lineJson", lineJson);
		params.put("f", f);
		
		response.getWriter().write(LongWaitHttpUtils.URLGet(
				"http://120.78.205.53:6080/arcgis/rest/services/chinaDEM/ImageServer/exts/contourSOE/getAverageSlope",
				params, LongWaitHttpUtils.URL_PARAM_DECODECHARSET_UTF8));
	}
	
	@ResponseBody
	@RequestMapping("/arcgis/rest/services/ztsj/MapServer/exts/ztsj/ztsjOverlay")
	public void ztsjOverlay(HttpServletResponse response,
			String type, String yearlevel, String lineJson, String f) throws IOException
	{
		Map<String, String> params = new HashMap<>();
		params.put("type", type);
		params.put("lineJson", lineJson);
		params.put("yearlevel", yearlevel);
		params.put("f", f);
		
		response.getWriter().write(LongWaitHttpUtils.URLGet(
				"http://120.78.205.53:6080/arcgis/rest/services/ztsj/MapServer/exts/ztsj/ztsjOverlay",
				params, LongWaitHttpUtils.URL_PARAM_DECODECHARSET_UTF8));
	}
	
	@ResponseBody
	@RequestMapping("/arcgis/rest/services/chinaDEM/ImageServer/exts/contourSOE/calcSlope")
	public void calcSlope(HttpServletResponse response,
			String lineJson, String f) throws IOException
	{
		Map<String, String> params = new HashMap<>();
		params.put("lineJson", lineJson);
		params.put("f", f);
		
		response.getWriter().write(LongWaitHttpUtils.URLGet(
				"http://120.78.205.53:6080/arcgis/rest/services/chinaDEM/ImageServer/exts/contourSOE/calcSlope",
				params, LongWaitHttpUtils.URL_PARAM_DECODECHARSET_UTF8));
	}
}
