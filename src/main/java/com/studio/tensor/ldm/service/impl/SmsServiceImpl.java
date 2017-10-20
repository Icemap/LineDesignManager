package com.studio.tensor.ldm.service.impl;

import java.util.HashMap;
import java.util.Map;

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
	TokenServiceImpl tokenServiceImpl;
	
	String signName;
	String registerTemplateCode;
	
	@PostConstruct
	public void onInit()
	{
		signName = "";
		registerTemplateCode = "";
	}
	
	@Override
	public ResultBean getRegisterCode(String phoneNum)
	{
		String code = tokenServiceImpl.getRegisterCode(phoneNum);
		Map<String, String> map = new HashMap<>();
		map.put("code", code);
		
		return ResultBean.tokenKeyValid(SmsUtils.smsSend(
				signName, map, phoneNum, registerTemplateCode));
	}

	@Override
	public ResultBean compareRegisterCode(String phoneNum, String code)
	{
		return ResultBean.tokenKeyValid(
				tokenServiceImpl.compareRegisterCode(phoneNum, code));
	}
}
