package com.studio.tensor.ldm.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.studio.tensor.ldm.bean.AdminSetting;
import com.studio.tensor.ldm.bean.PermissionTree;
import com.studio.tensor.ldm.dao.ApiInfoMapper;
import com.studio.tensor.ldm.dao.ApiRoleMapper;
import com.studio.tensor.ldm.dao.RoleInfoMapper;
import com.studio.tensor.ldm.pojo.ApiInfo;
import com.studio.tensor.ldm.pojo.ApiRole;
import com.studio.tensor.ldm.pojo.RoleInfo;
import com.studio.tensor.ldm.service.PermissionInfoService;
import com.studio.tensor.ldm.utils.FileUtils;

@Service
public class PermissionServiceImpl implements PermissionInfoService
{
	private PermissionTree permissionTree;
	
	private AdminSetting adminSetting;
	
	@Autowired
	RoleInfoMapper roleInfoMapper;
	
	@Autowired
	ApiInfoMapper apiInfoMapper;
	
	@Autowired
	ApiRoleMapper apiRoleMapper;
	
	@PostConstruct
	private void onInit()
	{
		adminSetting= new Gson().fromJson(
				FileUtils.readResourcesByLines("json_prop/admin_setting.json"),
				AdminSetting.class);
		permissionTree = new PermissionTree();
	}

	@Override
	public Boolean insertRole(String roleName, String des, String fontNodeId)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean deleteRole(Integer roleId)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean updateRole(RoleInfo roleInfo)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean insertAPI(String apiName, String url)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean deleteAPI(Integer apiId)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean updateAPI(ApiInfo apiInfo)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean insertApiRole(Integer apiId, Integer roleId)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean deleteApiRole(Integer apiRoleId)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean updateApiRole(ApiRole apiRole)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PermissionTree getAllTree()
	{
		List<RoleInfo> roleList = roleInfoMapper.selectAll();
		List<ApiInfo> apiList = apiInfoMapper.selectAll();
		List<ApiRole> apiRoleList = apiRoleMapper.selectAll();
		
		buildTree(roleList);
		
		return permissionTree;
	}
	
	private void buildTree(List<RoleInfo> roleList)
	{
		//初始化加入一系列根节点。
		for(RoleInfo role : roleList)
		{
			if(role.getFrontNodeId() == null)
			{
				PermissionTree child = new PermissionTree();
				child.role = role;
				permissionTree.child.add(child);
			}
		}
		
		//迭代
		//因为多根节点，需循环
		for(PermissionTree roots : permissionTree.child)
		{
			getTreeRecursion(roots, roleList);
		}
	}
	
	private void getTreeRecursion(PermissionTree node, List<RoleInfo> roleList)
	{
		Integer nodeId = node.role.getId();
		
		for(RoleInfo role : roleList)
		{
			Integer fontNodeId = role.getFrontNodeId();
			if(fontNodeId != null && fontNodeId.equals(nodeId))
			{
				PermissionTree childNode = new PermissionTree();
				childNode.role = role;
				node.child.add(childNode);
				getTreeRecursion(childNode, roleList);
			}
		}
	}
}
