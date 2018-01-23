package com.studio.tensor.ldm.utils;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.studio.tensor.ldm.bean.DrawPicBean;
import com.studio.tensor.ldm.bean.DrawPicBean.LabelBean;
import com.studio.tensor.ldm.bean.DrawPicBean.LocationBean;
import com.studio.tensor.ldm.bean.DrawPicBean.PointBean;
import com.studio.tensor.ldm.bean.DrawPicBean.PolygonBean;
import com.studio.tensor.ldm.bean.DrawPicBean.PolylineBean;
import com.studio.tensor.ldm.bean.LatLngInfo;

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
	
	public static class BackgroundUrlParam
	{
		public String backgroundUrl;
		public String coverUrl;
		public Boolean hasCover;
	}
	
	public static enum BackgroundType
	{
		Google_Satellite,
		Google_Image,
		Google_Terrain,
		AMap_Satellite,
		AMap_Image,
		TianDiTu_Satellite,
		TianDiTu_Image
	}
	
	private static String Google_Satellite_Url = "http://mt0.google.cn/vt/lyrs=y&hl=zh-CN&hl=zh-CN&gl=CN&x={x}&y={y}&z={z}&s=Gali";
	private static String Google_Image_Url = "http://mt0.google.cn/vt/lyrs=m&hl=zh-CN&hl=zh-CN&gl=CN&x={x}&y={y}&z={z}&s=Gali";
	private static String Google_Terrain_Url = "http://mt0.google.cn/vt/lyrs=p&hl=zh-CN&hl=zh-CN&gl=CN&x={x}&y={y}&z={z}&s=Gali";
	private static String AMap_Satellite_Url = "http://webst02.is.autonavi.com/appmaptile?style=6&x={x}&y={y}&z={z}";
	private static String AMap_Cover_Url = "http://webst02.is.autonavi.com/appmaptile?x={x}&y={y}&z={z}&lang=zh_cn&size=1&scale=1&style=8";
	private static String AMap_Image_Url = "http://webrd03.is.autonavi.com/appmaptile?lang=zh_cn&size=1&scale=1&style=8&x={x}&y={y}&z={z}";
	private static String TianDiTu_Satellite_Url = "http://t1.tianditu.cn/DataServer?T=img_w&X={x}&Y={y}&L={z}";
	private static String TianDiTu_Image_Url = "http://t1.tianditu.cn/DataServer?T=vec_w&X={x}&Y={y}&L={z}";
	private static String TianDiTu_Cover_Url = "http://t1.tianditu.cn/DataServer?T=cva_w&X={x}&Y={y}&L={z}";
	
	public static BufferedImage onDraw(DrawPicBean drawPicBean, Integer level,BackgroundType type)
	{
		GoogleMapDrawPrarm backgroundDrawParam = getBackgroundImageParam(drawPicBean, level);
		BufferedImage image = drawBackgroundImage(backgroundDrawParam, type);
		image = drawCover(drawPicBean, backgroundDrawParam, image);
		return image;
	}
	
	 public static Color toColorFromString(String colorStr)
	 {  
		colorStr = colorStr.substring(1);
		Integer a = Integer.parseInt(colorStr.substring(0, 2), 16);
		Integer r = Integer.parseInt(colorStr.substring(2, 4), 16);
		Integer g = Integer.parseInt(colorStr.substring(4, 6), 16);
		Integer b = Integer.parseInt(colorStr.substring(6, 8), 16);
		Color color =  new Color(r, g, b, a) ;  
		return color;
	 }  
	
	public static BufferedImage drawCover(DrawPicBean drawPicBean, 
			GoogleMapDrawPrarm backgroundDrawParam, BufferedImage image)
	{
		Integer picWidth = (backgroundDrawParam.rightBottomMapParam.x 
				- backgroundDrawParam.leftTopMapParam.x + 1) * GOOGLE_MAP_TILE_WIDTH;
		Integer picHeight = (backgroundDrawParam.rightBottomMapParam.y 
				- backgroundDrawParam.leftTopMapParam.y + 1) * GOOGLE_MAP_TILE_WIDTH;
		
		Double trueWidth = backgroundDrawParam.rightBottomMocCood.getLongitude() - 
				backgroundDrawParam.leftTopMocCood.getLongitude();
		Double trueHeight = backgroundDrawParam.rightBottomMocCood.getLatitude() - 
				backgroundDrawParam.leftTopMocCood.getLatitude();
		
		Graphics g = image.getGraphics();
		Graphics2D gHandle = (Graphics2D)g;
		BasicStroke bs = new BasicStroke(backgroundDrawParam.leftTopMapParam.z * backgroundDrawParam.leftTopMapParam.z / 24 + 1);
		gHandle.setStroke(bs);
		
		for(PolygonBean polygon : drawPicBean.polygon)
		{
			int[] xs = new int[polygon.coor.length];
			int[] ys = new int[polygon.coor.length];
			for(int i = 0;i < polygon.coor.length;i++)
			{
				LatLngInfo gcj02Loc = CoodUtils.gps84_To_Gcj02(polygon.coor[i].lat, polygon.coor[i].lon);
				LatLngInfo loc = CoodUtils.lonLatToGoogleMercator(gcj02Loc.getLongitude(), gcj02Loc.getLatitude());
				xs[i] = (int)((loc.getLongitude() - backgroundDrawParam.leftTopMocCood.getLongitude())
						/ trueWidth * picWidth);
				ys[i] = (int)((loc.getLatitude() - backgroundDrawParam.leftTopMocCood.getLatitude())
						/ trueHeight * picHeight);
			}
			gHandle.setColor(toColorFromString(polygon.color));
			gHandle.fillPolygon(xs, ys, polygon.coor.length);
		}
		
		for(PolylineBean polyline : drawPicBean.polyline)
		{
			int[] xs = new int[polyline.coor.length];
			int[] ys = new int[polyline.coor.length];
			for(int i = 0;i < polyline.coor.length;i++)
			{
				LatLngInfo gcj02Loc = CoodUtils.gps84_To_Gcj02(polyline.coor[i].lat, polyline.coor[i].lon);
				LatLngInfo loc = CoodUtils.lonLatToGoogleMercator(gcj02Loc.getLongitude(), gcj02Loc.getLatitude());
				xs[i] = (int)((loc.getLongitude() - backgroundDrawParam.leftTopMocCood.getLongitude())
						/ trueWidth * picWidth);
				ys[i] = (int)((loc.getLatitude() - backgroundDrawParam.leftTopMocCood.getLatitude())
						/ trueHeight * picHeight);
			}
			gHandle.setColor(toColorFromString(polyline.color));
			gHandle.drawPolyline(xs, ys, polyline.coor.length);
		}
		
		for(PointBean point : drawPicBean.point)
		{
			LatLngInfo gcj02Loc = CoodUtils.gps84_To_Gcj02(point.coor.lat, point.coor.lon);
			LatLngInfo loc = CoodUtils.lonLatToGoogleMercator(gcj02Loc.getLongitude(), gcj02Loc.getLatitude());
			drawIcon(gHandle, loc, point, backgroundDrawParam, trueWidth, trueHeight, picWidth, picHeight);
		}
		
		gHandle.setFont(new Font("楷体", Font.BOLD, backgroundDrawParam.leftTopMapParam.z 
				* backgroundDrawParam.leftTopMapParam.z / 6 + 1));
		for(LabelBean label : drawPicBean.label)
		{
			LatLngInfo gcj02Loc = CoodUtils.gps84_To_Gcj02(label.coor.lat, label.coor.lon);
			LatLngInfo loc = CoodUtils.lonLatToGoogleMercator(gcj02Loc.getLongitude(), gcj02Loc.getLatitude());
			gHandle.setColor(toColorFromString(label.color));
			gHandle.drawString(label.text, 
					(int)((loc.getLongitude() - backgroundDrawParam.leftTopMocCood.getLongitude())
							/ trueWidth * picWidth), 
					(int)((loc.getLatitude() - backgroundDrawParam.leftTopMocCood.getLatitude())
							/ trueHeight * picHeight));
		}
		gHandle.dispose();
		
		return image;
	}
	
	public static void drawIcon(Graphics gHandle, LatLngInfo loc, PointBean point,
			GoogleMapDrawPrarm backgroundDrawParam, Double trueWidth, Double trueHeight,
			Integer picWidth, Integer picHeight)
	{
		int ICON_WIDTH = backgroundDrawParam.leftTopMapParam.z * backgroundDrawParam.leftTopMapParam.z / 4;
		try
		{
			gHandle.drawImage(HttpUtils.getImage(point.pointIconUrl),
					(int)((loc.getLongitude() - backgroundDrawParam.leftTopMocCood.getLongitude())
							/ trueWidth * picWidth - ICON_WIDTH / 2), 
					(int)((loc.getLatitude() - backgroundDrawParam.leftTopMocCood.getLatitude())
							/ trueHeight * picHeight - ICON_WIDTH / 2), ICON_WIDTH, ICON_WIDTH, null);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			drawIcon(gHandle, loc, point, backgroundDrawParam, trueWidth,
					trueHeight, picWidth, picHeight);
		}
	}
	
	public static GoogleMapDrawPrarm getBackgroundImageParam(DrawPicBean drawPicBean, Integer level)
	{
		List<LocationBean> pointList = new ArrayList<>();
		
		for(PolygonBean polygon : drawPicBean.polygon)
			for(LocationBean loc : polygon.coor)
				pointList.add(loc);
		
		for(PolylineBean polyline : drawPicBean.polyline)
			for(LocationBean loc : polyline.coor)
				pointList.add(loc);
		
		for(DrawPicBean.PointBean point : drawPicBean.point)
			pointList.add(point.coor);
		
		GoogleMapDrawPrarm result = new GoogleMapDrawPrarm();
		
		if(pointList == null || pointList.size() == 0) return null;
		
		Double left = pointList.get(0).lon;
		Double right = pointList.get(0).lon;
		Double top = pointList.get(0).lat;
		Double bottom = pointList.get(0).lat;
		
		//得到点集的Envelop
		for(int i = 1;i < pointList.size();i++)
		{
			if(left > pointList.get(i).lon) left = pointList.get(i).lon;
			if(right < pointList.get(i).lon) right = pointList.get(i).lon;
			if(top < pointList.get(i).lat) top = pointList.get(i).lat;
			if(bottom > pointList.get(i).lat) bottom = pointList.get(i).lat;
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
	
	public static BufferedImage drawBackgroundImage(
			GoogleMapDrawPrarm backgroundDrawParam,BackgroundType type)
	{
		BufferedImage panel = new BufferedImage(
				(backgroundDrawParam.rightBottomMapParam.x - backgroundDrawParam.leftTopMapParam.x + 1) * GOOGLE_MAP_TILE_WIDTH, 
				(backgroundDrawParam.rightBottomMapParam.y - backgroundDrawParam.leftTopMapParam.y + 1) * GOOGLE_MAP_TILE_WIDTH,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = panel.getGraphics();
		Graphics2D gHandle = (Graphics2D)g;
		BasicStroke bs = new BasicStroke(backgroundDrawParam.leftTopMapParam.z);
		gHandle.setStroke(bs);
		//多线程部分
		Vector<Thread> threads = new Vector<Thread>();
		for(int x = backgroundDrawParam.leftTopMapParam.x ;
				x <= backgroundDrawParam.rightBottomMapParam.x;x++)
		{
			for(int y = backgroundDrawParam.leftTopMapParam.y ;
					y <= backgroundDrawParam.rightBottomMapParam.y;y++)
			{
				BackgroundUrlParam bup = buildUrl(x, y, backgroundDrawParam.rightBottomMapParam.z, type);
				
				final Integer fx = x;
				final Integer fy = y;
				Thread iThread = new Thread(new Runnable()
				{
					public void run()
					{
						drawPartPic(gHandle, bup, fx, fy, 
								backgroundDrawParam.leftTopMapParam.x, 
								backgroundDrawParam.leftTopMapParam.y);
					}
				});
				threads.add(iThread);
				iThread.start();
			}
		}
		
		for (Thread iThread : threads)
		{
			try
			{
				// 等待所有线程执行完毕
				iThread.join();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		
		gHandle.dispose();
		return panel;
	}

	public static void drawPartPic(Graphics gHandle, BackgroundUrlParam bup,
			Integer x, Integer y, Integer x0, Integer y0)
	{
		try
		{
			gHandle.drawImage(HttpUtils.getImage(bup.backgroundUrl), 
					(x - x0) * GOOGLE_MAP_TILE_WIDTH, 
					(y - y0) * GOOGLE_MAP_TILE_WIDTH,
					GOOGLE_MAP_TILE_WIDTH, GOOGLE_MAP_TILE_WIDTH, null);
			if(bup.hasCover)
			{
				gHandle.drawImage(HttpUtils.getImage(bup.coverUrl), 
						(x - x0) * GOOGLE_MAP_TILE_WIDTH, 
						(y - y0) * GOOGLE_MAP_TILE_WIDTH,
						GOOGLE_MAP_TILE_WIDTH, GOOGLE_MAP_TILE_WIDTH, null);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
			drawPartPic(gHandle, bup, x, y, x0, y0);
		}
	}
	
	public static BackgroundUrlParam buildUrl(Integer x, Integer y, Integer z, BackgroundType type)
	{
		BackgroundUrlParam result = new BackgroundUrlParam();
		switch (type)
		{
			case Google_Satellite:
				result.backgroundUrl = Google_Satellite_Url;
				result.coverUrl = "";
				result.hasCover = false;
				break;
			case Google_Image:
				result.backgroundUrl = Google_Image_Url;
				result.coverUrl = "";
				result.hasCover = false;
				break;
			case Google_Terrain:
				result.backgroundUrl = Google_Terrain_Url;
				result.coverUrl = "";
				result.hasCover = false;
				break;
			case AMap_Image:
				result.backgroundUrl = AMap_Image_Url;
				result.coverUrl = "";
				result.hasCover = false;
				break;
			case AMap_Satellite:
				result.backgroundUrl = AMap_Satellite_Url;
				result.coverUrl = AMap_Cover_Url;
				result.hasCover = true;
				break;
			case TianDiTu_Image:
				result.backgroundUrl = TianDiTu_Image_Url;
				result.coverUrl = TianDiTu_Cover_Url;
				result.hasCover = true;
				break;
			case TianDiTu_Satellite:
				result.backgroundUrl = TianDiTu_Satellite_Url;
				result.coverUrl = TianDiTu_Cover_Url;
				result.hasCover = true;
				break;
		}
		
		result.backgroundUrl = result.backgroundUrl.replace("{x}", x + "");
		result.backgroundUrl = result.backgroundUrl.replace("{y}", y + "");
		result.backgroundUrl = result.backgroundUrl.replace("{z}", z + "");
		if(result.hasCover)
		{
			result.coverUrl = result.coverUrl.replace("{x}", x + "");
			result.coverUrl = result.coverUrl.replace("{y}", y + "");
			result.coverUrl = result.coverUrl.replace("{z}", z + "");
		}
		return result;
	}
}
