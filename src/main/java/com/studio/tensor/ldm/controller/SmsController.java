package com.studio.tensor.ldm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.studio.tensor.ldm.bean.ResultBean;
import com.studio.tensor.ldm.service.impl.SmsServiceImpl;

@Controller
@RequestMapping("/sms")
public class SmsController
{
	@Autowired
	SmsServiceImpl smsServiceImpl;
	
	@ResponseBody
	@RequestMapping("/getRegisterCode")
	public ResultBean getRegisterCode(String phoneNum)
	{
		return smsServiceImpl.getRegisterCode(phoneNum);
	}
	
	@ResponseBody
	@RequestMapping("/compareRegisterCode")
	public ResultBean compareRegisterCode(String phoneNum, String code)
	{
		return smsServiceImpl.compareRegisterCode(phoneNum, code);
	}
	
}
