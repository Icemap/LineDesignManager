package com.studio.tensor.ldm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.studio.tensor.ldm.anno.RegisterToAPI;
import com.studio.tensor.ldm.anno.RegisterToAPIController;
import com.studio.tensor.ldm.bean.ResultBean;
import com.studio.tensor.ldm.pojo.UserInfo;
import com.studio.tensor.ldm.service.impl.UserInfoServiceImpl;
import com.studio.tensor.ldm.utils.HashUtils;
import com.studio.tensor.ldm.utils.StringUtils;

@Controller
@RegisterToAPIController
@RequestMapping("/user")
public class UserController
{
	@Autowired
	UserInfoServiceImpl userInfoServiceImpl;
	
	@ResponseBody
	@RequestMapping("/login")
	@RegisterToAPI(apiKey="user-login", apiValue="用户登录")
	public ResultBean userLogin(String account, String password)
	{
		return userInfoServiceImpl.userLogin(account, password, "user-login");
	}
	
	@ResponseBody
	@RequestMapping("/register")
	@RegisterToAPI(apiKey="user-register", apiValue="用户注册")
	public ResultBean userRegister(String account, String password)
	{
		return userInfoServiceImpl.userRegister(account, password);
	}
	
	@ResponseBody
	@RequestMapping("/update")
	@RegisterToAPI(apiKey="user-update", apiValue="用户信息补全")
	public ResultBean userUpdate(
			@RequestParam(value = "userId",required = true)Integer userId, 
			@RequestParam(value = "token",required = true)String token,
			@RequestParam(value = "userName",required = false)String userName,
			@RequestParam(value = "password",required = false)String password)
	{
		UserInfo userInfo = new UserInfo();
		userInfo.setId(userId);
		if(!StringUtils.isNullOrEmpty(userName))
			userInfo.setUserName(userName);
		if(!StringUtils.isNullOrEmpty(password))
			userInfo.setPassword(HashUtils.getMD5(password));
		
		return userInfoServiceImpl.userUpdate(userInfo, token, "user-update");
	}
}
