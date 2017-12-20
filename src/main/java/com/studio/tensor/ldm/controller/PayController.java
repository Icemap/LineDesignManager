package com.studio.tensor.ldm.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.studio.tensor.ldm.bean.ResultBean;
import com.studio.tensor.ldm.service.impl.RedisServiceImpl;
import com.studio.tensor.ldm.service.impl.UserInfoServiceImpl;
import com.studio.tensor.ldm.utils.HashUtils;

@Controller
@RequestMapping("/pay")
public class PayController
{
	private String PAY_SIGNED_STRING = "3102f5412c241643d0aace70c68773bd";
	
	@Autowired
	RedisServiceImpl redisServiceImpl;
	
	@Autowired
	UserInfoServiceImpl userInfoServiceImpl;
	
	@ResponseBody
	@RequestMapping("/getPayCode")
	public ResultBean getPayCode(Integer userId)
	{
		String code = HashUtils.getMD5(new Date().toString());
		String signedHashCode = HashUtils.getMD5(PAY_SIGNED_STRING + code);
		redisServiceImpl.setPayConfirmCode(userId, signedHashCode);
		return ResultBean.tokenKeyValid(code);
	}
	
	@ResponseBody
	@RequestMapping("/confirmAndUpdate")
	public ResultBean confirmAndUpdate(Integer userId,
			String signedHashCode, Integer roleId)
	{
		if(!redisServiceImpl.isPayConfirm(userId, signedHashCode))
			return ResultBean.tokenKeyNotValid();
		userInfoServiceImpl.userRoleIdUpdate(userId, roleId);
		return ResultBean.tokenKeyValid(true);
	}
}
