package com.studio.tensor.ldm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.studio.tensor.ldm.bean.GISTerrainBean;
import com.studio.tensor.ldm.bean.ResultBean;
import com.studio.tensor.ldm.pojo.CostPriceAdjustmentIndexInfo;
import com.studio.tensor.ldm.pojo.CostPriceInfo;
import com.studio.tensor.ldm.service.impl.CostPriceInfoServiceImpl;

@Controller
@RequestMapping("/costPrice")
public class CostPriceInfoController
{
	@Autowired
	CostPriceInfoServiceImpl costPriceInfoServiceImpl;
	
	@ResponseBody
	@RequestMapping("/insertCostPriceInfo")
	public ResultBean insertCostPriceInfo(String jsonCostPriceInfo)
	{
		CostPriceInfo costPriceInfo = new Gson()
				.fromJson(jsonCostPriceInfo, CostPriceInfo.class);
		
		return ResultBean.tokenKeyValid(
				costPriceInfoServiceImpl.insertCostPriceInfo(costPriceInfo));
	}
	
	@ResponseBody
	@RequestMapping("/updateCostPriceInfo")
	public ResultBean updateCostPriceInfo(String jsonCostPriceInfo)
	{
		CostPriceInfo costPriceInfo = new Gson()
				.fromJson(jsonCostPriceInfo, CostPriceInfo.class);
		
		return ResultBean.tokenKeyValid(
				costPriceInfoServiceImpl.updateCostPriceInfo(costPriceInfo));
	}
	
	@ResponseBody
	@RequestMapping("/deleteCostPriceInfo")
	public ResultBean deleteCostPriceInfo(Integer costPriceInfoId)
	{
		return ResultBean.tokenKeyValid(
				costPriceInfoServiceImpl.deleteCostPriceInfo(costPriceInfoId));
	}
	
	@ResponseBody
	@RequestMapping("/insertCostPriceAdjustmentIndexInfo")
	public ResultBean insertCostPriceAdjustmentIndexInfo(String jsonCostPriceAdjustmentIndexInfo)
	{
		CostPriceAdjustmentIndexInfo costPriceAdjustmentIndexInfo = new Gson()
				.fromJson(jsonCostPriceAdjustmentIndexInfo, CostPriceAdjustmentIndexInfo.class);
		
		return ResultBean.tokenKeyValid(costPriceInfoServiceImpl
				.insertCostPriceAdjustmentIndexInfo(costPriceAdjustmentIndexInfo));
	}
	
	@ResponseBody
	@RequestMapping("/updateCostPriceAdjustmentIndexInfo")
	public ResultBean updateCostPriceAdjustmentIndexInfo(String jsonCostPriceAdjustmentIndexInfo)
	{
		CostPriceAdjustmentIndexInfo costPriceAdjustmentIndexInfo = new Gson()
				.fromJson(jsonCostPriceAdjustmentIndexInfo, CostPriceAdjustmentIndexInfo.class);
		
		return ResultBean.tokenKeyValid(costPriceInfoServiceImpl
				.updateCostPriceAdjustmentIndexInfo(costPriceAdjustmentIndexInfo));
	}
	
	@ResponseBody
	@RequestMapping("/deleteCostPriceAdjustmentIndexInfo")
	public ResultBean deleteCostPriceAdjustmentIndexInfo(Integer costPriceAdjustmentIndexInfoId)
	{
		return ResultBean.tokenKeyValid(costPriceInfoServiceImpl
				.deleteCostPriceAdjustmentIndexInfo(costPriceAdjustmentIndexInfoId));
	}

	@ResponseBody
	@RequestMapping("/selectLibraryTag")
	public ResultBean selectLibraryTag()
	{
		return ResultBean.tokenKeyValid(costPriceInfoServiceImpl.selectLibraryTag());
	}

	@ResponseBody
	@RequestMapping("/selectCostPriceInfoByLibraryTag")
	public ResultBean selectCostPriceInfoByLibraryTag(String tag)
	{
		return ResultBean.tokenKeyValid(costPriceInfoServiceImpl.selectCostPriceInfoByLibraryTag(tag));
	}

	@ResponseBody
	@RequestMapping("/selectCostPriceAdjustmentIndexInfoByLibraryTag")
	public ResultBean selectCostPriceAdjustmentIndexInfoByLibraryTag(String tag)
	{
		return ResultBean.tokenKeyValid(costPriceInfoServiceImpl.selectCostPriceAdjustmentIndexInfoByLibraryTag(tag));
	}
	

	@ResponseBody
	@RequestMapping("/costPriceCalc")
	public ResultBean costPriceCalc(String tag,
			Integer rowNum, Integer sectionArea, Integer loopNum,
			Integer voltageLevel, Double iceHeight, Double windSpeed,
			Double humanLength, String jsonGisTerrainBeans)
	{
		List<GISTerrainBean> gisTerrainBeans = new Gson().fromJson(
				jsonGisTerrainBeans, new TypeToken<List<GISTerrainBean>>(){}.getType());
		
		return ResultBean.tokenKeyValid(costPriceInfoServiceImpl.costPriceCalc(
				tag, rowNum, sectionArea, loopNum, voltageLevel, 
				iceHeight, windSpeed, humanLength, gisTerrainBeans));
	}
}
