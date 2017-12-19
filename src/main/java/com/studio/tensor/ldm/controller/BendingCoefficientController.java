package com.studio.tensor.ldm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.studio.tensor.ldm.bean.ResultBean;
import com.studio.tensor.ldm.pojo.BendingCoefficientInfo;
import com.studio.tensor.ldm.service.impl.BendingCoefficientServiceImpl;

@Controller
@RequestMapping("/bendingCoefficient")
public class BendingCoefficientController
{
	@Autowired
	BendingCoefficientServiceImpl bendingCoefficientServiceImpl;
	
	@ResponseBody
	@RequestMapping("/get")
	public ResultBean getBendingCoefficient(Integer userId)
	{
		BendingCoefficientInfo bendingCoefficientInfo = bendingCoefficientServiceImpl.getBendingCoefficient(userId);
		if(bendingCoefficientInfo == null)
		{
			bendingCoefficientInfo = BendingCoefficientInfo.getDefault(userId);
			bendingCoefficientServiceImpl.insertBendingCoefficient(bendingCoefficientInfo);
		}
		return ResultBean.tokenKeyValid(bendingCoefficientInfo);
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public ResultBean updateBendingCoefficient(String sBendingCoefficientInfo)
	{
		BendingCoefficientInfo bendingCoefficientInfo = new Gson().fromJson(sBendingCoefficientInfo, BendingCoefficientInfo.class);
		return ResultBean.tokenKeyValid(bendingCoefficientServiceImpl.updateBendingCoefficient(bendingCoefficientInfo));
	}
}
