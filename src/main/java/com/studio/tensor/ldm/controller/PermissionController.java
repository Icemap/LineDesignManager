package com.studio.tensor.ldm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.studio.tensor.ldm.bean.ResultBean;
import com.studio.tensor.ldm.pojo.ApiInfo;
import com.studio.tensor.ldm.pojo.ApiRole;
import com.studio.tensor.ldm.pojo.RoleInfo;
import com.studio.tensor.ldm.service.impl.PermissionServiceImpl;
import com.studio.tensor.ldm.utils.ByteBooleanUtils;

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
	@RequestMapping("/getUserVisableRoleList")
	public ResultBean getUserVisableRoleList()
	{
		return ResultBean.tokenKeyValid(permissionServiceImpl.getAllUserVisableNode());
	}
	
	@ResponseBody
	@RequestMapping("/insertRole")
	public ResultBean insertRole(String roleName, String des,
			Long price, Boolean userVisible)
	{
		return ResultBean.tokenKeyValid(
				permissionServiceImpl.insertRole(roleName, des, price, userVisible));
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
	public ResultBean updateRole(Integer roleId, String roleName, String des,
			Long price, Boolean userVisible)
	{
		RoleInfo roleInfo = new RoleInfo();
		roleInfo.setId(roleId);
		roleInfo.setDes(des);
		roleInfo.setRoleName(roleName);
		roleInfo.setPrice(price);
		roleInfo.setUserVisible(ByteBooleanUtils.boolean2Byte(userVisible));
		
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
	@RequestMapping("/insertApiRole/muti")
	public ResultBean insertApiRoleMuti(String jsonApiRoles)
	{
		List<ApiRole> apiRoles = new Gson().fromJson(
				jsonApiRoles, new TypeToken<List<ApiRole>>() {}.getType());
		return ResultBean.tokenKeyValid(
				permissionServiceImpl.insertApiRoleMuti(apiRoles));
	}
	
	@ResponseBody
	@RequestMapping("/deleteApiRole")
	public ResultBean deleteApiRole(Integer apiRoleId)
	{
		return ResultBean.tokenKeyValid(
				permissionServiceImpl.deleteApiRole(apiRoleId));
	}
	
	@ResponseBody
	@RequestMapping("/deleteApiRole/muti")
	public ResultBean deleteApiRoleMuti(String jsonApiRoleIds)
	{
		List<Integer> apiRoleIds = new Gson().fromJson(
				jsonApiRoleIds, new TypeToken<List<Integer>>() {}.getType());
		return ResultBean.tokenKeyValid(
				permissionServiceImpl.deleteApiRoleMuti(apiRoleIds));
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
	
	@ResponseBody
	@RequestMapping("/updateApiRole/muti")
	public ResultBean updateApiRoleMuti(String jsonApiRoles)
	{
		List<ApiRole> apiRoles = new Gson().fromJson(
				jsonApiRoles, new TypeToken<List<ApiRole>>() {}.getType());
		return ResultBean.tokenKeyValid(
				permissionServiceImpl.updateApiRoleMuti(apiRoles));
	}
	
	@ResponseBody
	@RequestMapping("/getAPIList")
	public ResultBean getAPIList()
	{
		return ResultBean.tokenKeyValid(permissionServiceImpl.getAPIList());
	}
}
