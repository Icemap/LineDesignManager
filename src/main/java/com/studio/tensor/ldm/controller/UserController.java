package com.studio.tensor.ldm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.studio.tensor.ldm.bean.ResultBean;
import com.studio.tensor.ldm.pojo.UserStatus;
import com.studio.tensor.ldm.service.impl.UserInfoServiceImpl;

@Controller
@RequestMapping("/user")
public class UserController
{
	@Autowired
	UserInfoServiceImpl userInfoServiceImpl;
	
	@ResponseBody
	@RequestMapping("/login")
	public ResultBean userLogin(String phoneNum, String password)
	{
		return userInfoServiceImpl.userLogin(phoneNum, password);
	}
	
	@ResponseBody
	@RequestMapping("/register/sendCode")
	public ResultBean userRegisterSendCode(String phoneNum)
	{
		return userInfoServiceImpl.userRegisterSendCode(phoneNum);
	}
	
	@ResponseBody
	@RequestMapping("/register")
	public ResultBean userRegister(String phoneNum, String password, String code)
	{
		return userInfoServiceImpl.userRegister(phoneNum, password, code);
	}
	
	@ResponseBody
	@RequestMapping("/nickname/update")
	public ResultBean userUpdateNickName(Integer id, String nickName)
	{
		return userInfoServiceImpl.userUpdateNickName(id, nickName);
	}
	
	@ResponseBody
	@RequestMapping("/icon/update")
	public ResultBean userUpdateIcon(Integer id, MultipartFile icon)
	{
		return userInfoServiceImpl.userUpdateIcon(id, icon);
	}
	
	@ResponseBody
	@RequestMapping("/password/update/sendCode")
	public ResultBean userForgetPasswordRequest(String phoneNum)
	{
		return userInfoServiceImpl.userForgetPasswordRequest(phoneNum);
	}
	
	@ResponseBody
	@RequestMapping("/password/update")
	public ResultBean userForgetPasswordChange(
			String phoneNum, String confirmCode, String newPassword)
	{
		return userInfoServiceImpl.userForgetPasswordChange(
				phoneNum, confirmCode, newPassword);
	}
	
	@ResponseBody
	@RequestMapping("/loginBackground")
	public ResultBean loginBackground(String adminAccount, String adminPassword)
	{
		return userInfoServiceImpl.userLoginBackground(adminAccount, adminPassword);
	}
	
	@ResponseBody
	@RequestMapping("/getAllUser")
	public ResultBean getAllUser(Integer start, Integer size)
	{
		return ResultBean.tokenKeyValid(userInfoServiceImpl.getAllUser(start, size));
	}
	
	@ResponseBody
	@RequestMapping("/getUserNumber")
	public ResultBean getUserNumber()
	{
		return ResultBean.tokenKeyValid(userInfoServiceImpl.getUserNumber().intValue());
	}
	
	@ResponseBody
	@RequestMapping("/adminUpdateUserRole")
	public ResultBean adminUpdateUserRole(Integer userId, Integer roleId)
	{
		return ResultBean.tokenKeyValid(userInfoServiceImpl.userRoleIdUpdate(userId, roleId));
	}
	
	@ResponseBody
	@RequestMapping("/adminAddUser")
	public ResultBean adminAddUser(String phoneNum, String password, Integer roleId)
	{
		return ResultBean.tokenKeyValid(userInfoServiceImpl.userInsert(phoneNum, password, roleId));
	}
	
	@ResponseBody
	@RequestMapping("/adminDeleteUser")
	public ResultBean adminDeleteUser(Integer userId)
	{
		return ResultBean.tokenKeyValid(userInfoServiceImpl.userDelete(userId));
	}
	
	@ResponseBody
	@RequestMapping("/userStatusSet")
	public ResultBean userStatusSet(Integer userId, String statusJson)
	{
		UserStatus userStatus = new UserStatus();
		userStatus.setUserId(userId);
		userStatus.setStatusJson(statusJson);
		return ResultBean.tokenKeyValid(userInfoServiceImpl.userStatusSet(userStatus));
	}
	
	@ResponseBody
	@RequestMapping("/userStatusGet")
	public ResultBean userStatusGet(Integer userId)
	{
		return ResultBean.tokenKeyValid(userInfoServiceImpl.userStatusGet(userId));
	}
	
	@ResponseBody
	@RequestMapping("/getByUserIds")
	public ResultBean getByUserIds(String jsonUserIds)
	{
		List<Integer> userIds = new Gson().fromJson(jsonUserIds,
				new TypeToken<List<Integer>>(){}.getType());
		return ResultBean.tokenKeyValid(userInfoServiceImpl.getByUserIds(userIds));
	}
}
