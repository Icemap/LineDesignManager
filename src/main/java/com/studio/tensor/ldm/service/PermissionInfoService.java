package com.studio.tensor.ldm.service;

import java.util.List;

import com.studio.tensor.ldm.bean.PermissionNode;
import com.studio.tensor.ldm.pojo.ApiInfo;
import com.studio.tensor.ldm.pojo.ApiRole;
import com.studio.tensor.ldm.pojo.OrderInfo;
import com.studio.tensor.ldm.pojo.RoleInfo;

public interface PermissionInfoService
{
	//API
	Boolean insertApi(ApiInfo apiInfo);
	Boolean updateApi(ApiInfo apiInfo);
	Boolean deleteApi(Integer apiInfoId);
	List<ApiInfo> selectAllApi();
	
	//Role
	Boolean insertRole(RoleInfo roleInfo);
	Boolean updateRole(RoleInfo roleInfo);
	Boolean deleteRole(Integer roleInfoId);
	List<RoleInfo> selectAllRole();
	
	//API-Role
	Boolean insertRoleApis(List<ApiRole> apiRoleInfos);
	Boolean deleteRoleApis(List<Integer> apiRoleInfoIds);
	
	//Order
	Boolean insertOrder(OrderInfo orderInfo);
	Boolean deleteOrder(Integer orderInfoId);
	Boolean isExpired(Integer userId);
	List<OrderInfo> selectOrderInfo(Integer start, Integer size);
	Integer selectOrderInfoNum();
	
	//Tree
	List<PermissionNode> getAllNode();
	List<ApiInfo> getApiList();
}
