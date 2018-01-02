package com.studio.tensor.ldm.test;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.studio.tensor.ldm.bean.LatLngInfo;
import com.studio.tensor.ldm.bean.PolylineBean.PointBean;
import com.studio.tensor.ldm.utils.CoodUtils;
import com.studio.tensor.ldm.utils.HttpUtils;

public class DrawPicUtils
{
	private static Integer GOOGLE_MAP_TILE_WIDTH = 256;
	private static Double WEB_MOC_HALF_WIDTH = 20037508.3427892;
	
	public static class GoogleMapParam
	{
		public Integer x;
		public Integer y;
		public Integer z;
	}
	
	public static class GoogleMapDrawPrarm
	{
		public GoogleMapParam leftTopMapParam;
		public GoogleMapParam rightBottomMapParam;
		public LatLngInfo leftTopMocCood;
		public LatLngInfo rightBottomMocCood;
	}
	
	
	public static void onDraw(List<PointBean> pointList, Integer level)
	{
		GoogleMapDrawPrarm backgroundDrawParam = getBackgroundImageParam(pointList, level);
		BufferedImage image = drawBackgroundImage(backgroundDrawParam);
		try
		{
			ImageIO.write(image, "png", new File("E://test.png"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static GoogleMapDrawPrarm getBackgroundImageParam(List<PointBean> pointList, Integer level)
	{
		GoogleMapDrawPrarm result = new GoogleMapDrawPrarm();
		
		if(pointList == null || pointList.size() == 0) return null;
		
		Double left = pointList.get(0).x;
		Double right = pointList.get(0).x;
		Double top = pointList.get(0).y;
		Double bottom = pointList.get(0).y;
		
		//得到点集的Envelop
		for(int i = 1;i < pointList.size();i++)
		{
			if(left > pointList.get(i).x) left = pointList.get(i).x;
			if(right < pointList.get(i).x) right = pointList.get(i).x;
			if(top < pointList.get(i).y) top = pointList.get(i).y;
			if(bottom > pointList.get(i).y) bottom = pointList.get(i).y;
		}
		
		result.leftTopMapParam = new GoogleMapParam();
		result.rightBottomMapParam = new GoogleMapParam();
		result.leftTopMapParam.z = level;
		result.rightBottomMapParam.z = level;
		
		//创建点集Envelop的Google Moc坐标
		LatLngInfo leftTopMocPoint = CoodUtils.lonLatToGoogleMercator(left, top);
		LatLngInfo rightBottomMocPoint = CoodUtils.lonLatToGoogleMercator(right, bottom);
		
		Double perTileWidth = WEB_MOC_HALF_WIDTH * 2 / Math.pow(2, level);
		
		result.leftTopMapParam.x = (int)(leftTopMocPoint.getLongitude() / perTileWidth);
		result.leftTopMapParam.y = (int)(leftTopMocPoint.getLatitude() / perTileWidth);
		result.rightBottomMapParam.x = (int)(rightBottomMocPoint.getLongitude() / perTileWidth);
		result.rightBottomMapParam.y = (int)(rightBottomMocPoint.getLatitude() / perTileWidth);
		
		result.leftTopMocCood = new LatLngInfo();
		result.rightBottomMocCood = new LatLngInfo();
		result.leftTopMocCood.setLongitude(result.leftTopMapParam.x * perTileWidth);
		result.leftTopMocCood.setLatitude(result.leftTopMapParam.y * perTileWidth);
		result.rightBottomMocCood.setLongitude((result.rightBottomMapParam.x + 1) * perTileWidth);
		result.rightBottomMocCood.setLatitude((result.rightBottomMapParam.y + 1) * perTileWidth);
		
		return result;
	}
	
	public static BufferedImage drawBackgroundImage(GoogleMapDrawPrarm backgroundDrawParam)
	{
		BufferedImage panel = new BufferedImage(
				(backgroundDrawParam.rightBottomMapParam.x - backgroundDrawParam.leftTopMapParam.x + 1) * GOOGLE_MAP_TILE_WIDTH, 
				(backgroundDrawParam.rightBottomMapParam.y - backgroundDrawParam.leftTopMapParam.y + 1) * GOOGLE_MAP_TILE_WIDTH,
				BufferedImage.TYPE_INT_RGB);
		Graphics gHandle = panel.getGraphics();
		for(int x = backgroundDrawParam.leftTopMapParam.x ;
				x <= backgroundDrawParam.rightBottomMapParam.x;x++)
		{
			for(int y = backgroundDrawParam.leftTopMapParam.y ;
					y <= backgroundDrawParam.rightBottomMapParam.y;y++)
			{
				Integer subDomin = (int)(Math.random() * 4) + 1;
				//http://webrd01.is.autonavi.com/appmaptile?lang=zh_cn&size=1&scale=1&style=8&x=204&y=108&z=8
				String url = "http://webrd0"
						+ subDomin + ".is.autonavi.com/appmaptile?lang=zh_cn&size=1&scale=1&style=8&x="
						+ x	+ "&y="
						+ y + "&z="
						+ backgroundDrawParam.rightBottomMapParam.z;
				
				gHandle.drawImage(HttpUtils.getImage(url), 
						(x - backgroundDrawParam.leftTopMapParam.x) * GOOGLE_MAP_TILE_WIDTH, 
						(y - backgroundDrawParam.leftTopMapParam.y) * GOOGLE_MAP_TILE_WIDTH,
						GOOGLE_MAP_TILE_WIDTH, GOOGLE_MAP_TILE_WIDTH, null);
				
				System.out.println("x=" + x + ",y=" + y);
			}
		}
		gHandle.dispose();
		return panel;
	}

	public static void main(String[] args)
	{
		String lineJson = "[{\"x\":113.17531585693361,\"y\":23.091417455868857},{\"x\":113.27041625976562,\"y\":23.11225968329776},{\"x\":113.39263916015626,\"y\":23.109101977956197},{\"x\":113.38645935058595,\"y\":23.0253957508145},{\"x\":113.23711395263673,\"y\":23.018760201342253},{\"x\":113.14682006835939,\"y\":23.067413310425852},{\"x\":113.15437316894533,\"y\":23.148252272743257}]";
		List<PointBean> pointList = new Gson().fromJson(lineJson, new TypeToken<List<PointBean>>(){}.getType());
		onDraw(pointList, 16);
		System.out.print("Done!!!");
	}
}
