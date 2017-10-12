package com.studio.tensor.ldm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.studio.tensor.ldm.utils.AngelUtils;

@Controller
@RequestMapping("/calc")
public class CalculatorController
{
	@ResponseBody
	@RequestMapping("/angel")
	public Double calcuAngel(Double cLon, Double cLat,
			Double sLon, Double sLat, Double eLon, Double eLat)
	{
		return AngelUtils.getAngle(cLon, cLat, sLon, sLat, eLon, eLat);
	}
}
