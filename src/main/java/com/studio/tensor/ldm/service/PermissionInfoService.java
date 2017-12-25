package com.studio.tensor.ldm.service;

import java.util.List;

import com.studio.tensor.ldm.bean.PermissionNode;
import com.studio.tensor.ldm.pojo.ApiInfo;
import com.studio.tensor.ldm.pojo.ApiRole;
import com.studio.tensor.ldm.pojo.RoleInfo;

public interface PermissionInfoService
{
	//Role
	Boolean insertRole(String roleName, String des,
			Long price, Boolean userVisible);
	Boolean deleteRole(Integer roleId);
	Boolean updateRole(RoleInfo roleInfo);
	
	//API
	Boolean insertAPI(String apiName, String url);
	Boolean deleteAPI(Integer apiId);
	Boolean updateAPI(ApiInfo apiInfo);
	List<ApiInfo> getAPIList();
	
	//API-Role
	Boolean insertApiRole(Integer apiId, Integer roleId);
	Boolean deleteApiRole(Integer apiRoleId);
	Boolean updateApiRole(ApiRole apiRole);
	
	//Tree
	List<PermissionNode> getAllNode();
	List<PermissionNode> getAllUserVisableNode();
	
	//Muti
	Boolean insertApiRoleMuti(List<ApiRole> apiRoles);
	Boolean deleteApiRoleMuti(List<Integer> apiRoleIds);
	Boolean updateApiRoleMuti(List<ApiRole> apiRoles);
}
