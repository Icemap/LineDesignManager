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
	private Map<String, List<Integer>> apiAllowMap;
	
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
		buildApiAllowList();
	}

	@Override
	public Boolean insertRole(String roleName, String des)
	{
		RoleInfo roleInfo = new RoleInfo();
		roleInfo.setRoleName(roleName);
		roleInfo.setDes(des);
		roleInfoMapper.insertSelective(roleInfo);
		buildNodeList();
		buildApiAllowList();
		return true;
	}

	@Override
	public Boolean deleteRole(Integer roleId)
	{
		roleInfoMapper.deleteByPrimaryKey(roleId);
		buildNodeList();
		buildApiAllowList();
		return true;
	}

	@Override
	public Boolean updateRole(RoleInfo roleInfo)
	{
		roleInfoMapper.updateByPrimaryKey(roleInfo);
		buildNodeList();
		buildApiAllowList();
		return true;
	}

	@Override
	public Boolean insertAPI(String apiName, String url)
	{
		ApiInfo apiInfo = new ApiInfo();
		apiInfo.setApiName(apiName);
		apiInfo.setUrl(url);
		apiInfoMapper.insertSelective(apiInfo);
		buildNodeList();
		buildApiAllowList();
		return true;
	}

	@Override
	public Boolean deleteAPI(Integer apiId)
	{
		apiInfoMapper.deleteByPrimaryKey(apiId);
		buildNodeList();
		buildApiAllowList();
		return true;
	}

	@Override
	public Boolean updateAPI(ApiInfo apiInfo)
	{
		apiInfoMapper.updateByPrimaryKeySelective(apiInfo);
		buildNodeList();
		buildApiAllowList();
		return true;
	}

	@Override
	public Boolean insertApiRole(Integer apiId, Integer roleId)
	{
		ApiRole apiRole = new ApiRole();
		apiRole.setApiId(apiId);
		apiRole.setRoleId(roleId);
		apiRoleMapper.insertSelective(apiRole);
		buildNodeList();
		buildApiAllowList();
		return true;
	}

	@Override
	public Boolean deleteApiRole(Integer apiRoleId)
	{
		apiRoleMapper.deleteByPrimaryKey(apiRoleId);
		buildNodeList();
		buildApiAllowList();
		return true;
	}

	@Override
	public Boolean updateApiRole(ApiRole apiRole)
	{
		apiRoleMapper.updateByPrimaryKeySelective(apiRole);
		buildNodeList();
		buildApiAllowList();
		return true;
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


	private void buildApiAllowList()
	{
		apiAllowMap = new HashMap<>();
		List<ApiInfo> apiInfoList = apiInfoMapper.selectAll();
		List<ApiRole> apiRolelist = apiRoleMapper.selectAll();
		for(ApiInfo api : apiInfoList)
		{
			apiAllowMap.put(api.getUrl(), getApiAllowRole(apiRolelist, api.getId()));
		}
	}
	
	private List<Integer> getApiAllowRole(List<ApiRole> apiRolelist, Integer apiId)
	{
		List<Integer> result = new ArrayList<>();
		for(ApiRole apiRole : apiRolelist)
		{
			if(apiRole.getApiId().equals(apiId))
				result.add(apiRole.getRoleId());
		}
		
		return result;
	}
	
	public Boolean isApiExist(String apiPath)
	{
		return apiAllowMap.containsKey(apiPath);
	}
	
	public Boolean isRoleAllowThisApi(String apiPath, Integer roleId)
	{
		return apiAllowMap.get(apiPath).contains(roleId);
	}
}
