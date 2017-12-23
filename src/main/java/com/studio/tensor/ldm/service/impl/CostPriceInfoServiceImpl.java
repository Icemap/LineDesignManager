package com.studio.tensor.ldm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studio.tensor.ldm.bean.CostPriceCalculateResultBean;
import com.studio.tensor.ldm.bean.CostPriceCalculateResultBean.TerrainAndPriceBean;
import com.studio.tensor.ldm.bean.GISTerrainBean;
import com.studio.tensor.ldm.bean.GISTerrainBean.GISTerrainSolve;
import com.studio.tensor.ldm.dao.CostPriceAdjustmentIndexInfoMapper;
import com.studio.tensor.ldm.dao.CostPriceInfoMapper;
import com.studio.tensor.ldm.pojo.CostPriceAdjustmentIndexInfo;
import com.studio.tensor.ldm.pojo.CostPriceInfo;
import com.studio.tensor.ldm.service.CostPriceInfoService;

@Service
public class CostPriceInfoServiceImpl implements CostPriceInfoService
{
	@Autowired
	CostPriceInfoMapper costPriceInfoMapper;
	
	@Autowired
	CostPriceAdjustmentIndexInfoMapper costPriceAdjustmentIndexInfoMapper;
	
	@Override
	public Boolean insertCostPriceInfo(CostPriceInfo costPriceInfo)
	{
		return costPriceInfoMapper.insertSelective(costPriceInfo) == 1;
	}

	@Override
	public Boolean updateCostPriceInfo(CostPriceInfo costPriceInfo)
	{
		return costPriceInfoMapper.updateByPrimaryKeySelective(costPriceInfo) == 1;
	}

	@Override
	public Boolean deleteCostPriceInfo(Integer costPriceInfoId)
	{
		return costPriceInfoMapper.deleteByPrimaryKey(costPriceInfoId) == 1;
	}

	@Override
	public Boolean insertCostPriceAdjustmentIndexInfo(CostPriceAdjustmentIndexInfo costPriceAdjustmentIndexInfo)
	{
		return costPriceAdjustmentIndexInfoMapper.insertSelective(costPriceAdjustmentIndexInfo) == 1;
	}

	@Override
	public Boolean updateCostPriceAdjustmentIndexInfo(CostPriceAdjustmentIndexInfo costPriceAdjustmentIndexInfo)
	{
		return costPriceAdjustmentIndexInfoMapper.updateByPrimaryKeySelective(costPriceAdjustmentIndexInfo) == 1;
	}

	@Override
	public Boolean deleteCostPriceAdjustmentIndexInfo(Integer costPriceAdjustmentIndexInfoId)
	{
		return costPriceAdjustmentIndexInfoMapper.deleteByPrimaryKey(costPriceAdjustmentIndexInfoId) == 1;
	}

	@Override
	public List<String> selectLibraryTag()
	{
		return costPriceInfoMapper.getTag();
	}

	@Override
	public List<CostPriceInfo> selectCostPriceInfoByLibraryTag(String tag)
	{
		return costPriceInfoMapper.selectByTag(tag);
	}

	@Override
	public List<CostPriceAdjustmentIndexInfo> selectCostPriceAdjustmentIndexInfoByLibraryTag(String tag)
	{
		return costPriceAdjustmentIndexInfoMapper.selectByTag(tag);
	}

	@Override
	public CostPriceCalculateResultBean costPriceCalc(String tag,
			Integer rowNum, Integer sectionArea, Integer loopNum,
			Integer voltageLevel, Double iceHeight, Double windSpeed,
			Double humanLength, List<GISTerrainBean> gisTerrainBeans)
	{
		CostPriceCalculateResultBean result = new CostPriceCalculateResultBean();
		List<CostPriceInfo> costPriceInfos = costPriceInfoMapper.selectByMsg(tag, rowNum, sectionArea, loopNum, voltageLevel);
		result.iceIndexs = costPriceAdjustmentIndexInfoMapper.selectIceInfoByMsg(tag, voltageLevel, loopNum, iceHeight);
		result.windIndexs = costPriceAdjustmentIndexInfoMapper.selectWindInfoByMsg(tag, voltageLevel, windSpeed);
		result.humanIndexs = costPriceAdjustmentIndexInfoMapper.selectHumanInfoByMsg(tag, voltageLevel, humanLength);
		
		//本体造价不能少的
		if(costPriceInfos == null || costPriceInfos.size() == 0)
		{
			result.errorCode = 1;
			result.errorMsg = "线路参数错误或造价库无数据";
			return result;
		}
		
		//解析地形
		List<GISTerrainSolve> solveTerrain = GISTerrainBean.solveTerrain(gisTerrainBeans);
		for(GISTerrainSolve solve : solveTerrain)
			result.terrainAndPriceBeans.add(new TerrainAndPriceBean(solve));
		
		//填入静态造价标准
		for(TerrainAndPriceBean bean : result.terrainAndPriceBeans)
		{
			CostPriceInfo costPrice = getCostPriceInfo(bean.terrainName, costPriceInfos);
			bean.selfPrice = costPrice.getSelfPrice();
			bean.extraPrice = costPrice.getExtraPrice();
			bean.staticPrice = costPrice.getStaticPrice();
		}
		
		//计算本体与静态造价
		result.makeStaticPrice();
		
		//计算动态造价
		result.makeDynamicPrice();
		
		return result;
	}
	
	private CostPriceInfo getCostPriceInfo(String terrainName, List<CostPriceInfo> costPriceInfos)
	{
		for(CostPriceInfo bean : costPriceInfos)
		{
			if(bean.getTerrainName().equals(terrainName))
				return bean;
		}
		return null;
	}
}
