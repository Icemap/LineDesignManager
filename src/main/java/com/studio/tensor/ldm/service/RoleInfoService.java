package com.studio.tensor.ldm.service;

import com.studio.tensor.ldm.bean.ResultBean;
import com.studio.tensor.ldm.pojo.RoleInfo;
import com.studio.tensor.ldm.pojo.UserInfo;

public interface RoleInfoService
{
	//直接方法
	ResultBean getAllRole(String adminAccount, String adminPassword);
	ResultBean createRole(String adminAccount, String adminPassword,
			RoleInfo roleInfo);
	ResultBean deleteRole(String adminAccount, String adminPassword,
			Integer roleId);
	ResultBean updateRole(String adminAccount, String adminPassword,
			RoleInfo roleInfo);
	ResultBean updateUserRoleId(String adminAccount, String adminPassword,
			UserInfo userInfo);
	
	//间接方法
	Boolean hasPermission(Integer roleId, String apiKey);
}
