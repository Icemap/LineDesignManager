package com.studio.tensor.ldm.bean;

import java.util.ArrayList;
import java.util.List;

import com.studio.tensor.ldm.bean.GISTerrainBean.GISTerrainSolve;
import com.studio.tensor.ldm.pojo.CostPriceAdjustmentIndexInfo;

public class CostPriceCalculateResultBean
{
	public Integer errorCode;
	public String errorMsg;
	public List<TerrainAndPriceBean> terrainAndPriceBeans;
	public Double standardStateSelfPrice;
	public Double standardStateStaticPrice;
	public List<CostPriceAdjustmentIndexInfo> iceIndexs;
	public List<CostPriceAdjustmentIndexInfo> windIndexs;
	public List<CostPriceAdjustmentIndexInfo> humanIndexs;
	public Double iceIndexTotal;
	public Double windIndexTotal;
	public Double humanIndexTotal;
	
	public Double iceSelfPrice;
	public Double iceStaticPrice;
	public Double iceWindSelfPrice;
	public Double iceWindStaticPrice;
	public Double iceWindHumanSelfPrice;
	public Double iceWindHumanStaticPrice;
	
	public Double totalSelfPrice;
	public Double totalStaticPrice;
	public Double totalDynamicPrice;
	
	public CostPriceCalculateResultBean()
	{
		terrainAndPriceBeans = new ArrayList<>();
	}
	
	public static class TerrainAndPriceBean
	{
		public String terrainName;
		public Double weight;
		public Double length;
		public Double selfPrice;
		public Double extraPrice;
		public Double staticPrice;
		public Double selfPriceTotal;
		public Double staticPriceTotal;
		
		public TerrainAndPriceBean()
		{
			
		}
		
		public TerrainAndPriceBean(GISTerrainSolve terrain)
		{
			this.terrainName = terrain.terrainName;
			this.weight = terrain.weight;
			this.length = terrain.length;
		}
	}
	
	public void makeStaticPrice()
	{
		standardStateSelfPrice = 0.0;
		standardStateStaticPrice = 0.0;
		
		for(TerrainAndPriceBean bean: terrainAndPriceBeans)
		{
			Double selfTerrainPrice = bean.length * bean.selfPrice;
			Double staticTerrainPrice = bean.length * bean.staticPrice;
			
			standardStateSelfPrice += selfTerrainPrice;
			standardStateStaticPrice += staticTerrainPrice;
			
			bean.selfPriceTotal = selfTerrainPrice;
			bean.staticPriceTotal = staticTerrainPrice;
		}
	}
	
	public void makeDynamicPrice()
	{
		iceIndexTotal = 1.0;
		windIndexTotal = 1.0;
		humanIndexTotal = 1.0;
		
		for(CostPriceAdjustmentIndexInfo iceIndex : iceIndexs)
			iceIndexTotal *= iceIndex.getAdjustmentIndex();
		for(CostPriceAdjustmentIndexInfo windIndex : windIndexs)
			windIndexTotal *= windIndex.getAdjustmentIndex();
		for(CostPriceAdjustmentIndexInfo humanIndex : humanIndexs)
			humanIndexTotal *= humanIndex.getAdjustmentIndex();
		
		iceSelfPrice = standardStateSelfPrice * iceIndexTotal;
		iceStaticPrice = standardStateStaticPrice * iceIndexTotal;
		iceWindSelfPrice = iceSelfPrice * windIndexTotal;
		iceWindStaticPrice = iceStaticPrice * windIndexTotal;
		iceWindHumanSelfPrice = iceWindSelfPrice * humanIndexTotal;
		iceWindHumanStaticPrice = iceWindStaticPrice * humanIndexTotal;
		
		totalSelfPrice = iceWindHumanSelfPrice;
		totalStaticPrice = iceWindHumanStaticPrice;
		totalDynamicPrice = iceWindHumanStaticPrice / 0.98;
	}
}
