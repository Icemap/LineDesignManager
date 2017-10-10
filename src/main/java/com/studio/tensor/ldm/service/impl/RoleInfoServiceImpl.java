package com.studio.tensor.ldm.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.studio.tensor.ldm.bean.AdminSetting;
import com.studio.tensor.ldm.bean.ResultBean;
import com.studio.tensor.ldm.bean.RoleCacheInfo;
import com.studio.tensor.ldm.dao.RoleInfoMapper;
import com.studio.tensor.ldm.dao.UserInfoMapper;
import com.studio.tensor.ldm.pojo.RoleInfo;
import com.studio.tensor.ldm.pojo.UserInfo;
import com.studio.tensor.ldm.service.RoleInfoService;
import com.studio.tensor.ldm.utils.FileUtils;

@Service
public class RoleInfoServiceImpl implements RoleInfoService
{
	private Map<Integer, RoleCacheInfo> cacheMap;
	private AdminSetting adminSetting;
	
	@Autowired
	RoleInfoMapper roleInfoMapper;
	
	@Autowired
	UserInfoMapper userInfoMapper;
	
	@PostConstruct
	private void onInit()
	{
		cacheMap = new HashMap<>();
		refreshCacheMap();
		
		adminSetting= new Gson().fromJson(
				FileUtils.readResourcesByLines("json_prop/admin_setting.json"),
				AdminSetting.class);
	}
	
	@Override
	public ResultBean getAllRole(String adminAccount, String adminPassword)
	{
		if(!adminSetting.isAdmin(adminAccount, adminPassword))
			return ResultBean.isNotAdmin();
		
		return ResultBean.tokenKeyValid(cacheMap);
	}

	@Override
	public ResultBean createRole(String adminAccount, String adminPassword, RoleInfo roleInfo)
	{
		if(!adminSetting.isAdmin(adminAccount, adminPassword))
			return ResultBean.isNotAdmin();
		if(roleInfoMapper.insertSelective(roleInfo) == 1)
			refreshCacheMap();
		return ResultBean.tokenKeyValid(true);
	}

	@Override
	public ResultBean deleteRole(String adminAccount, String adminPassword, Integer roleId)
	{
		if(!adminSetting.isAdmin(adminAccount, adminPassword))
			return ResultBean.isNotAdmin();
		if(roleInfoMapper.deleteByPrimaryKey(roleId) == 1)
			refreshCacheMap();
		return ResultBean.tokenKeyValid(true);
	}

	@Override
	public ResultBean updateRole(String adminAccount, String adminPassword, RoleInfo roleInfo)
	{
		if(!adminSetting.isAdmin(adminAccount, adminPassword))
			return ResultBean.isNotAdmin();
		
		if(roleInfoMapper.updateByPrimaryKeySelective(roleInfo) == 1)
			refreshCacheMap();

		return ResultBean.tokenKeyValid(true);
	}

	@Override
	public Boolean hasPermission(Integer roleId, String apiKey)
	{
		return cacheMap.get(roleId).getApiList().contains(apiKey);
	}
	
	private void refreshCacheMap()
	{
		List<RoleInfo> roleList = roleInfoMapper.getAllRoleInfo();
		cacheMap.clear();
		for(RoleInfo roleInfo : roleList)
		{
			cacheMap.put(roleInfo.getId(), new RoleCacheInfo(roleInfo));
		}
	}

	@Override
	public ResultBean updateUserRoleId(String adminAccount, String adminPassword, UserInfo userInfo)
	{
		if(!adminSetting.isAdmin(adminAccount, adminPassword))
			return ResultBean.isNotAdmin();
		
		return ResultBean.tokenKeyValid(userInfoMapper.updateByPrimaryKeySelective(userInfo) == 1);
	}
}
