package com.studio.tensor.ldm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.studio.tensor.ldm.bean.ResultBean;
import com.studio.tensor.ldm.pojo.UserInfo;
import com.studio.tensor.ldm.service.impl.UserInfoServiceImpl;
import com.studio.tensor.ldm.utils.StringUtils;

@Controller
@RequestMapping("/user")
public class UserController
{
	@Autowired
	UserInfoServiceImpl userInfoServiceImpl;
	
	@ResponseBody
	@RequestMapping("/login")
	public ResultBean userLogin(String account, String password)
	{
		return userInfoServiceImpl.userLogin(account, password);
	}
	
	@ResponseBody
	@RequestMapping("/register")
	public ResultBean userRegister(String account, String password)
	{
		return userInfoServiceImpl.userRegister(account, password);
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public ResultBean userUpdate(
			@RequestParam(value = "userId",required = true)Integer userId, 
			@RequestParam(value = "token",required = true)String token,
			@RequestParam(value = "userName",required = false)String userName,
			@RequestParam(value = "roleId",required = false)Integer roleId)
	{
		UserInfo userInfo = new UserInfo();
		userInfo.setId(userId);
		if(!StringUtils.isNullOrEmpty(userName))
			userInfo.setUserName(userName);
		if(roleId != null)
			userInfo.setRoleId(roleId);
		
		return userInfoServiceImpl.userUpdate(userInfo, token);
	}
}
