package com.studio.tensor.ldm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.studio.tensor.ldm.bean.FileSetting;
import com.studio.tensor.ldm.bean.ResultBean;
import com.studio.tensor.ldm.utils.KMLUtils;

@Controller
@RequestMapping("/kml")
public class KMLController
{
	@Autowired
	FileSetting fileSetting;
	
	@ResponseBody
	@RequestMapping("/write")
	public ResultBean writeKML(String drawKMLBeanJson, String docName)
	{
		String fileName = docName + ".kml";
		KMLUtils.writeKML(drawKMLBeanJson, docName, fileSetting.getSaveFilePath() + fileName);
		return ResultBean.tokenKeyValid(fileSetting.getGetFilePath() + fileName);
	}
}
