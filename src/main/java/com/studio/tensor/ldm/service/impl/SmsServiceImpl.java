package com.studio.tensor.ldm.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studio.tensor.ldm.bean.ResultBean;
import com.studio.tensor.ldm.service.SmsService;
import com.studio.tensor.ldm.utils.SmsUtils;

@Service
public class SmsServiceImpl implements SmsService
{
	@Autowired
	RedisServiceImpl redisServiceImpl;
	
	String signName;
	String registerTemplateCode;
	
	@PostConstruct
	public void onInit()
	{
		signName = "线路设计助手";
		registerTemplateCode = "SMS_105720045";
	}
	
	@Override
	public ResultBean getRegisterCode(String phoneNum)
	{
		String code = getConfirmCode();
		redisServiceImpl.setConfirmCode(phoneNum, code);
		
		Map<String, String> map = new HashMap<>();
		map.put("code", code);
		
		return ResultBean.tokenKeyValid(SmsUtils.smsSend(
				signName, map, phoneNum, registerTemplateCode));
	}

	@Override
	public ResultBean compareRegisterCode(String phoneNum, String code)
	{
		return ResultBean.tokenKeyValid(
				redisServiceImpl.getConfirmCode(phoneNum, code));
	}
	
	private String getConfirmCode()
	{
		String left = (new Date().getTime() + "");
		left = left.substring(left.length() - 3, left.length());
		
		Random random = new Random(new Date().getTime());
		String right = random.nextInt(100) + "";
		
		return left + right;
	}
}
