package com.studio.tensor.ldm.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.studio.tensor.ldm.bean.DrawPicBean;
import com.studio.tensor.ldm.bean.FileSetting;
import com.studio.tensor.ldm.bean.ResultBean;
import com.studio.tensor.ldm.utils.DrawPicUtils;
import com.studio.tensor.ldm.utils.DrawPicUtils.BackgroundType;

@Controller
@RequestMapping("/draw")
public class DrawController
{
	@Autowired
	FileSetting fileSetting;
	
	/*
	 * Google_Satellite,
		Google_Image,
		Google_Terrain,
		AMap_Satellite,
		AMap_Image,
		TianDiTu_Satellite,
		TianDiTu_Image
	 */
	@ResponseBody
	@RequestMapping("/output/raster")
	public ResultBean outputRaster(String drawPicBeanJson, Integer level, String type,
			String picType)
	{
		if(!picType.equals("png") && !picType.equals("jpg") && !picType.equals("bmp"))
			return ResultBean.picTypeIllegal();
		
		DrawPicBean drawPicBean = new Gson().fromJson(drawPicBeanJson, DrawPicBean.class);
		
		BackgroundType bType = null;
		if(type.equals("Google_Satellite")) bType = BackgroundType.Google_Satellite;
		if(type.equals("Google_Image")) bType = BackgroundType.Google_Image;
		if(type.equals("Google_Terrain")) bType = BackgroundType.Google_Terrain;
		if(type.equals("AMap_Satellite")) bType = BackgroundType.AMap_Satellite;
		if(type.equals("AMap_Image")) bType = BackgroundType.AMap_Image;
		if(type.equals("TianDiTu_Satellite")) bType = BackgroundType.TianDiTu_Satellite;
		if(type.equals("TianDiTu_Image")) bType = BackgroundType.TianDiTu_Image;
		
		if(bType == null)
			return ResultBean.mapTypeIllegal();
		
		if(bType.equals(BackgroundType.Google_Satellite) || 
				bType.equals(BackgroundType.Google_Image) ||
				bType.equals(BackgroundType.Google_Terrain)||
				bType.equals(BackgroundType.AMap_Satellite)||
				bType.equals(BackgroundType.AMap_Image))
			drawPicBean = DrawPicUtils.WGS84toGCJ02(drawPicBean);
		
		BufferedImage image = DrawPicUtils.onDraw(drawPicBean, level, bType);
		String fileName =  new Date().getTime() + "." + picType;
		try
		{
			ImageIO.write(image, picType, new File(fileSetting.getSaveFilePath() + fileName));
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return ResultBean.makePicError();
		}
		return ResultBean.tokenKeyValid(fileSetting.getGetFilePath() + fileName);
	}
}
