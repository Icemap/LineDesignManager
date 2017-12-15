package com.studio.tensor.ldm.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studio.tensor.ldm.bean.PermissionNode;
import com.studio.tensor.ldm.dao.ApiInfoMapper;
import com.studio.tensor.ldm.dao.ApiRoleMapper;
import com.studio.tensor.ldm.dao.RoleInfoMapper;
import com.studio.tensor.ldm.pojo.ApiInfo;
import com.studio.tensor.ldm.pojo.ApiRole;
import com.studio.tensor.ldm.pojo.RoleInfo;
import com.studio.tensor.ldm.service.PermissionInfoService;

@Service
public class PermissionServiceImpl implements PermissionInfoService
{
	private List<PermissionNode> permissionNodeList;
	
	@Autowired
	RoleInfoMapper roleInfoMapper;
	
	@Autowired
	ApiInfoMapper apiInfoMapper;
	
	@Autowired
	ApiRoleMapper apiRoleMapper;
	
	@PostConstruct
	private void onInit()
	{
		buildNodeList(); 
	}

	@Override
	public Boolean insertRole(String roleName, String des)
	{
		RoleInfo roleInfo = new RoleInfo();
		roleInfo.setRoleName(roleName);
		roleInfo.setDes(des);
		return roleInfoMapper.insertSelective(roleInfo) == 1;
	}

	@Override
	public Boolean deleteRole(Integer roleId)
	{
		return roleInfoMapper.deleteByPrimaryKey(roleId) == 1;
	}

	@Override
	public Boolean updateRole(RoleInfo roleInfo)
	{
		return roleInfoMapper.updateByPrimaryKey(roleInfo) == 1;
	}

	@Override
	public Boolean insertAPI(String apiName, String url)
	{
		ApiInfo apiInfo = new ApiInfo();
		apiInfo.setApiName(apiName);
		apiInfo.setUrl(url);
		return apiInfoMapper.insertSelective(apiInfo) == 1;
	}

	@Override
	public Boolean deleteAPI(Integer apiId)
	{
		return apiInfoMapper.deleteByPrimaryKey(apiId) == 1;
	}

	@Override
	public Boolean updateAPI(ApiInfo apiInfo)
	{
		return apiInfoMapper.updateByPrimaryKeySelective(apiInfo) == 1;
	}

	@Override
	public Boolean insertApiRole(Integer apiId, Integer roleId)
	{
		ApiRole apiRole = new ApiRole();
		apiRole.setApiId(apiId);
		apiRole.setRoleId(roleId);
		return apiRoleMapper.insertSelective(apiRole) == 1;
	}

	@Override
	public Boolean deleteApiRole(Integer apiRoleId)
	{
		return apiRoleMapper.deleteByPrimaryKey(apiRoleId) == 1;
	}

	@Override
	public Boolean updateApiRole(ApiRole apiRole)
	{
		return apiRoleMapper.updateByPrimaryKeySelective(apiRole) == 1;
	}

	@Override
	public List<PermissionNode> getAllNode()
	{
		return permissionNodeList;
	}
	
	private void buildNodeList()
	{
		permissionNodeList = new ArrayList<>();
		List<RoleInfo> roleList = roleInfoMapper.selectAll();
		List<ApiInfo> apiList = apiInfoMapper.selectAll();
		List<ApiRole> apiRoleList = apiRoleMapper.selectAll();
		
		Map<Integer, Integer> apiIdMap = new HashMap<>();
		Map<Integer, Integer> roleIdMap = new HashMap<>();
		for(int i = 0; i < apiList.size();i++)
			apiIdMap.put(apiList.get(i).getId(), i);
		
		for(int i = 0; i < roleList.size();i++)
		{
			PermissionNode node = new PermissionNode();
			node.role = roleList.get(i);
			permissionNodeList.add(node);
			roleIdMap.put(roleList.get(i).getId(), i);
		}
		
		for(ApiRole apiRole : apiRoleList)
		{
			Integer apiListIndex = apiIdMap.get(apiRole.getApiId());
			Integer roleListIndex = roleIdMap.get(apiRole.getRoleId());
			permissionNodeList
				.get(roleListIndex)
				.allowList.add(apiList.get(apiListIndex));
		}
	}
}
