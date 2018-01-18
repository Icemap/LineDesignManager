package com.studio.tensor.ldm.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.studio.tensor.ldm.bean.ResultBean;
import com.studio.tensor.ldm.dao.UserInfoMapper;
import com.studio.tensor.ldm.pojo.ApiInfo;
import com.studio.tensor.ldm.pojo.ApiRole;
import com.studio.tensor.ldm.pojo.OrderInfo;
import com.studio.tensor.ldm.pojo.RoleInfo;
import com.studio.tensor.ldm.pojo.UserInfo;
import com.studio.tensor.ldm.service.impl.PermissionServiceImpl;
import com.studio.tensor.ldm.utils.ByteBooleanUtils;

@Controller
@RequestMapping("/permission")
public class PermissionController
{
	@Autowired
	PermissionServiceImpl permissionServiceImpl;
	
	@Autowired
	UserInfoMapper userInfoMapper;
	
	@ResponseBody
	@RequestMapping("/getPermissionList")
	public ResultBean getPermissionList()
	{
		return ResultBean.tokenKeyValid(permissionServiceImpl.getAllNode());
	}
	
	@ResponseBody
	@RequestMapping("/insertRole")
	public ResultBean insertRole(String roleName, String des,
			Long price1, Long price2, Long price3, Long price5, 
			Boolean userVisible, Boolean isDefaultRole, Double maxLength, Integer maxNum)
	{
		RoleInfo roleInfo = new RoleInfo();
		roleInfo.setDes(des);
		roleInfo.setRoleName(roleName);
		roleInfo.setPrice1(price1);
		roleInfo.setPrice2(price2);
		roleInfo.setPrice3(price3);
		roleInfo.setPrice5(price5);
		roleInfo.setIsDefaultRole(ByteBooleanUtils.boolean2Byte(isDefaultRole));
		roleInfo.setUserVisible(ByteBooleanUtils.boolean2Byte(userVisible));
		roleInfo.setLength(maxLength);
		roleInfo.setNum(maxNum);
		
		return ResultBean.tokenKeyValid(
				permissionServiceImpl.insertRole(roleInfo));
	}
	
	@ResponseBody
	@RequestMapping("/deleteRole")
	public ResultBean deleteRole(Integer roleId)
	{
		permissionServiceImpl.deleteApiRoleByRoleId(roleId);
		return ResultBean.tokenKeyValid(
				permissionServiceImpl.deleteRole(roleId));
	}
	
	@ResponseBody
	@RequestMapping("/updateRole")
	public ResultBean updateRole(Integer roleId, String roleName, String des,
			Long price1, Long price2, Long price3, Long price5, 
			Boolean userVisible, Boolean isDefaultRole, Double maxLength, Integer maxNum)
	{
		RoleInfo roleInfo = new RoleInfo();
		roleInfo.setId(roleId);
		roleInfo.setDes(des);
		roleInfo.setRoleName(roleName);
		roleInfo.setPrice1(price1);
		roleInfo.setPrice2(price2);
		roleInfo.setPrice3(price3);
		roleInfo.setPrice5(price5);
		roleInfo.setIsDefaultRole(ByteBooleanUtils.boolean2Byte(isDefaultRole));
		roleInfo.setUserVisible(ByteBooleanUtils.boolean2Byte(userVisible));
		roleInfo.setLength(maxLength);
		roleInfo.setNum(maxNum);
		
		return ResultBean.tokenKeyValid(
				permissionServiceImpl.updateRole(roleInfo));
	}
	
	@ResponseBody
	@RequestMapping("/insertAPI")
	public ResultBean insertAPI(String url, 
			String apiName, Boolean isCalcLength, Boolean isCalcNum)
	{
		ApiInfo apiInfo = new ApiInfo();
		apiInfo.setUrl(url);
		apiInfo.setApiName(apiName);
		apiInfo.setIsCalcLength(ByteBooleanUtils.boolean2Byte(isCalcLength));
		apiInfo.setIsCalcNum(ByteBooleanUtils.boolean2Byte(isCalcNum));
		return ResultBean.tokenKeyValid(
				permissionServiceImpl.insertApi(apiInfo));
	}
	
	@ResponseBody
	@RequestMapping("/deleteAPI")
	public ResultBean deleteAPI(Integer apiId)
	{
		permissionServiceImpl.deleteApiRoleByApiId(apiId);
		return ResultBean.tokenKeyValid(
				permissionServiceImpl.deleteApi(apiId));
	}
	
	@ResponseBody
	@RequestMapping("/updateAPI")
	public ResultBean updateAPI(Integer apiId, String url, 
			String apiName, Boolean isCalcLength, Boolean isCalcNum)
	{
		ApiInfo apiInfo = new ApiInfo();
		apiInfo.setId(apiId);
		apiInfo.setUrl(url);
		apiInfo.setApiName(apiName);
		apiInfo.setIsCalcLength(ByteBooleanUtils.boolean2Byte(isCalcLength));
		apiInfo.setIsCalcNum(ByteBooleanUtils.boolean2Byte(isCalcNum));
		return ResultBean.tokenKeyValid(
				permissionServiceImpl.updateApi(apiInfo));
	}
	
	@ResponseBody
	@RequestMapping("/insertApiRole/muti")
	public ResultBean insertApiRoleMuti(String jsonApiRoleIds)
	{
		List<ApiRole> apiRoles = new Gson().fromJson(
				jsonApiRoleIds, new TypeToken<List<ApiRole>>() {}.getType());
		return ResultBean.tokenKeyValid(
				permissionServiceImpl.insertRoleApis(apiRoles));
	}
	@ResponseBody
	@RequestMapping("/deleteApiRole/muti")
	public ResultBean deleteApiRoleMuti(String jsonApiRoleIds)
	{
		List<Integer> apiRoleIds = new Gson().fromJson(
				jsonApiRoleIds, new TypeToken<List<Integer>>() {}.getType());
		return ResultBean.tokenKeyValid(
				permissionServiceImpl.deleteRoleApis(apiRoleIds));
	}
	
	@ResponseBody
	@RequestMapping("/getAPIList")
	public ResultBean getAPIList()
	{
		return ResultBean.tokenKeyValid(permissionServiceImpl.getApiList());
	}
	
	@SuppressWarnings("deprecation")
	@ResponseBody
	@RequestMapping("/buyForRole")
	public ResultBean buyForRole(Integer userId, Integer roleId, Integer years)
	{
		Date now = new Date();
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setOrderUserId(userId);
		orderInfo.setOrderRoleId(roleId);
		orderInfo.setOrderStartTime(now);
		now.setYear(now.getYear() + years);
		orderInfo.setOrderEndTime(now);
		permissionServiceImpl.insertOrder(orderInfo);
		UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userId);
		userInfo.setRoleId(roleId);
		userInfoMapper.updateByPrimaryKeySelective(userInfo);
		return ResultBean.tokenKeyValid(true);
	}
	
	@ResponseBody
	@RequestMapping("/getAllOrder")
	public ResultBean getAllOrder(Integer start, Integer size)
	{
		return ResultBean.tokenKeyValid(permissionServiceImpl.selectOrderInfo(start, size));
	}
	
	@ResponseBody
	@RequestMapping("/getOrderNum")
	public ResultBean getOrderNum()
	{
		return ResultBean.tokenKeyValid(permissionServiceImpl.selectOrderInfoNum());
	}
}
