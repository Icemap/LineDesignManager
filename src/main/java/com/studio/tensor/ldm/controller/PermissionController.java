package com.studio.tensor.ldm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.studio.tensor.ldm.bean.ResultBean;
import com.studio.tensor.ldm.pojo.RoleInfo;
import com.studio.tensor.ldm.pojo.UserInfo;
import com.studio.tensor.ldm.service.impl.APIServiceImpl;
import com.studio.tensor.ldm.service.impl.RoleInfoServiceImpl;
import com.studio.tensor.ldm.service.impl.UserInfoServiceImpl;

@Controller
@RequestMapping("/permission")
public class PermissionController
{
	@Autowired
	APIServiceImpl apiServiceImpl;
	
	@Autowired
	RoleInfoServiceImpl roleInfoServiceImpl;
	
	@Autowired
	UserInfoServiceImpl userInfoServiceImpl;
	
	@ResponseBody
	@RequestMapping("/getApiMap")
	public ResultBean getApiMap(String adminAccount, String adminPassword)
	{
		return apiServiceImpl.getApiMap(adminAccount, adminPassword);
	}
	
	@ResponseBody
	@RequestMapping("/getRoleMap")
	public ResultBean getAllRole(String adminAccount, String adminPassword)
	{
		return roleInfoServiceImpl.getAllRole(adminAccount, adminPassword);
	}
	
	@ResponseBody
	@RequestMapping("/createRole")
	public ResultBean createRole(String adminAccount, String adminPassword,
			String roleName, String roleDes, String roleAPIJson)
	{
		RoleInfo roleInfo = new RoleInfo();
		roleInfo.setName(roleName);
		roleInfo.setDes(roleDes);
		roleInfo.setApiJson(roleAPIJson);
		return roleInfoServiceImpl.createRole(adminAccount, adminPassword, roleInfo);
	}
	
	@ResponseBody
	@RequestMapping("/updateRole")
	public ResultBean updateRole(String adminAccount, String adminPassword,
			Integer roleId, String roleName, String roleDes, String roleAPIJson)
	{
		RoleInfo roleInfo = new RoleInfo();
		roleInfo.setId(roleId);
		roleInfo.setName(roleName);
		roleInfo.setDes(roleDes);
		roleInfo.setApiJson(roleAPIJson);
		return roleInfoServiceImpl.updateRole(adminAccount, adminPassword, roleInfo);
	}
	
	@ResponseBody
	@RequestMapping("/deleteRole")
	public ResultBean updateRole(String adminAccount, String adminPassword,Integer roleId)
	{
		return roleInfoServiceImpl.deleteRole(adminAccount, adminPassword, roleId);
	}
	
	@ResponseBody
	@RequestMapping("/updateUserRoleId")
	public ResultBean updateUserRoleId(String adminAccount, String adminPassword,
			Integer roleId, Integer userId)
	{
		UserInfo userInfo = new UserInfo();
		userInfo.setId(userId);
		userInfo.setRoleId(roleId);
		
		return roleInfoServiceImpl.updateUserRoleId(
				adminAccount, adminPassword, userInfo);
	}
}
