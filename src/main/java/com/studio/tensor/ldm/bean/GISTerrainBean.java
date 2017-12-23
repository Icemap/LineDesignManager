package com.studio.tensor.ldm.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.studio.tensor.ldm.utils.CoodUtils;

public class GISTerrainBean
{
	public String TerrainName;
	public Double aveSlope;
	public Double[][] path;
	
	public static class GISTerrainSolve
	{
		public String terrainName;
		public Double weight;
		public Double length;
		
		public GISTerrainSolve()
		{
			terrainName = "";
			weight = 0.0;
			length = 0.0;
		}
		
		public GISTerrainSolve(String terrainName)
		{
			this.terrainName = terrainName;
			weight = 0.0;
			length = 0.0;
		}
	}
	
	public static List<GISTerrainSolve> solveTerrain(List<GISTerrainBean> src)
	{
		Map<String, Integer> terrainIndexMap = new HashMap<>();
		terrainIndexMap.put("平地", 0);
		terrainIndexMap.put("丘陵", 1);
		terrainIndexMap.put("山地", 2);
		terrainIndexMap.put("高山", 3);
		terrainIndexMap.put("峻岭", 4);
		terrainIndexMap.put("河网", 5);
		terrainIndexMap.put("泥沼", 6);
		
		List<GISTerrainSolve> result = new ArrayList<>();
		result.add(new GISTerrainSolve("平地"));
		result.add(new GISTerrainSolve("丘陵"));
		result.add(new GISTerrainSolve("山地"));
		result.add(new GISTerrainSolve("高山"));
		result.add(new GISTerrainSolve("峻岭"));
		result.add(new GISTerrainSolve("河网"));
		result.add(new GISTerrainSolve("泥沼"));
		
		Double allLength = 0.0;
		for(GISTerrainBean bean : src)
		{
			Integer listIndex = terrainIndexMap.get(bean.TerrainName);
			Double pathLength = getPathLength(bean.path);
			result.get(listIndex).length += pathLength;
			allLength += pathLength;
		}
		
		for(GISTerrainSolve res : result)
		{
			res.weight = res.length / allLength * 100;
			res.length /= 1000;
		}
		
		return result;
	}
	
	private static Double getPathLength(Double[][] path)
	{
		List<LatLngInfo> mocPoints = new ArrayList<>(); 
		for(Double[] point : path)
			mocPoints.add(CoodUtils.lonLatToMercator(point[0], point[1]));
		
		Double result = 0.0;
		for(int i = 1; i < mocPoints.size(); i++)
			result += getPointLength(mocPoints.get(i - 1), mocPoints.get(i));
		
		return result;
	}
	
	private static Double getPointLength(LatLngInfo p1, LatLngInfo p2)
	{
		return Math.sqrt(Math.pow((p1.getLongitude() - p2.getLongitude()), 2.0) +
				Math.pow((p1.getLatitude() - p2.getLatitude()), 2.0));
	}
}
