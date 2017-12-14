package com.studio.tensor.ldm.service;

import com.studio.tensor.ldm.bean.PermissionTree;
import com.studio.tensor.ldm.pojo.ApiInfo;
import com.studio.tensor.ldm.pojo.ApiRole;
import com.studio.tensor.ldm.pojo.RoleInfo;

public interface PermissionInfoService
{
	//Role
	Boolean insertRole(String roleName, String des, String fontNodeId);
	Boolean deleteRole(Integer roleId);
	Boolean updateRole(RoleInfo roleInfo);
	
	//API
	Boolean insertAPI(String apiName, String url);
	Boolean deleteAPI(Integer apiId);
	Boolean updateAPI(ApiInfo apiInfo);
	
	//API-Role
	Boolean insertApiRole(Integer apiId, Integer roleId);
	Boolean deleteApiRole(Integer apiRoleId);
	Boolean updateApiRole(ApiRole apiRole);
	
	//Tree
	PermissionTree getAllTree();
}
