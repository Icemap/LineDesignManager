package com.studio.tensor.ldm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.studio.tensor.ldm.bean.ResultBean;
import com.studio.tensor.ldm.service.impl.UserInfoServiceImpl;

@Controller
@RequestMapping("/user")
public class UserController
{
	@Autowired
	UserInfoServiceImpl userInfoServiceImpl;
	
	@ResponseBody
	@RequestMapping("/login")
	ResultBean userLogin(String phoneNum, String password)
	{
		return userInfoServiceImpl.userLogin(phoneNum, password);
	}
	
	@ResponseBody
	@RequestMapping("/register/sendCode")
	ResultBean userRegisterSendCode(String phoneNum)
	{
		return userInfoServiceImpl.userRegisterSendCode(phoneNum);
	}
	
	@ResponseBody
	@RequestMapping("/register")
	ResultBean userRegister(String phoneNum, String password, String code)
	{
		return userInfoServiceImpl.userRegister(phoneNum, password, code);
	}
	
	@ResponseBody
	@RequestMapping("/nickname/update")
	ResultBean userUpdateNickName(Integer id, String nickName)
	{
		return userInfoServiceImpl.userUpdateNickName(id, nickName);
	}
	
	@ResponseBody
	@RequestMapping("/icon/update")
	ResultBean userUpdateIcon(Integer id, MultipartFile icon)
	{
		return userInfoServiceImpl.userUpdateIcon(id, icon);
	}
	
	@ResponseBody
	@RequestMapping("/password/update/sendCode")
	ResultBean userForgetPasswordRequest(String phoneNum)
	{
		return userInfoServiceImpl.userForgetPasswordRequest(phoneNum);
	}
	
	@ResponseBody
	@RequestMapping("/password/update")
	ResultBean userForgetPasswordChange(
			String phoneNum, String confirmCode, String newPassword)
	{
		return userInfoServiceImpl.userForgetPasswordChange(
				phoneNum, confirmCode, newPassword);
	}
}
