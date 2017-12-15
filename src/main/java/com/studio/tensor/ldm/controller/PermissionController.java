package com.studio.tensor.ldm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.studio.tensor.ldm.bean.ResultBean;
import com.studio.tensor.ldm.pojo.ApiInfo;
import com.studio.tensor.ldm.pojo.ApiRole;
import com.studio.tensor.ldm.pojo.RoleInfo;
import com.studio.tensor.ldm.service.impl.PermissionServiceImpl;

@Controller
@RequestMapping("/permission")
public class PermissionController
{
	@Autowired
	PermissionServiceImpl permissionServiceImpl;
	
	@ResponseBody
	@RequestMapping("/getPermissionList")
	public ResultBean getPermissionList()
	{
		return ResultBean.tokenKeyValid(permissionServiceImpl.getAllNode());
	}
	
	@ResponseBody
	@RequestMapping("/insertRole")
	public ResultBean insertRole(String roleName, String des)
	{
		return ResultBean.tokenKeyValid(
				permissionServiceImpl.insertRole(roleName, des));
	}
	
	@ResponseBody
	@RequestMapping("/deleteRole")
	public ResultBean deleteRole(Integer roleId)
	{
		return ResultBean.tokenKeyValid(
				permissionServiceImpl.deleteRole(roleId));
	}
	
	@ResponseBody
	@RequestMapping("/updateRole")
	public ResultBean updateRole(Integer roleId, String roleName, String des)
	{
		RoleInfo roleInfo = new RoleInfo();
		roleInfo.setId(roleId);
		roleInfo.setDes(des);
		roleInfo.setRoleName(roleName);
		return ResultBean.tokenKeyValid(
				permissionServiceImpl.updateRole(roleInfo));
	}
	
	@ResponseBody
	@RequestMapping("/insertAPI")
	public ResultBean insertAPI(String apiName, String url)
	{
		return ResultBean.tokenKeyValid(
				permissionServiceImpl.insertAPI(apiName, url));
	}
	
	@ResponseBody
	@RequestMapping("/deleteAPI")
	public ResultBean deleteAPI(Integer apiId)
	{
		return ResultBean.tokenKeyValid(
				permissionServiceImpl.deleteAPI(apiId));
	}
	
	@ResponseBody
	@RequestMapping("/updateAPI")
	public ResultBean updateAPI(Integer apiId, String url, String apiName)
	{
		ApiInfo apiInfo = new ApiInfo();
		apiInfo.setId(apiId);
		apiInfo.setUrl(url);
		apiInfo.setApiName(apiName);
		return ResultBean.tokenKeyValid(
				permissionServiceImpl.updateAPI(apiInfo));
	}
	
	@ResponseBody
	@RequestMapping("/insertApiRole")
	public ResultBean insertApiRole(Integer apiId, Integer roleId)
	{
		return ResultBean.tokenKeyValid(
				permissionServiceImpl.insertApiRole(apiId, roleId));
	}
	
	@ResponseBody
	@RequestMapping("/deleteApiRole")
	public ResultBean deleteApiRole(Integer apiRoleId)
	{
		return ResultBean.tokenKeyValid(
				permissionServiceImpl.deleteApiRole(apiRoleId));
	}
	
	@ResponseBody
	@RequestMapping("/updateApiRole")
	public ResultBean updateApiRole(Integer apiRoleId, Integer roleId, Integer apiId)
	{
		ApiRole apiRole = new ApiRole();
		apiRole.setId(apiRoleId);
		apiRole.setApiId(apiId);
		apiRole.setRoleId(roleId);
		return ResultBean.tokenKeyValid(
				permissionServiceImpl.updateApiRole(apiRole));
	}
}
