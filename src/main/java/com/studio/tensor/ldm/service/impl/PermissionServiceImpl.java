package com.studio.tensor.ldm.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studio.tensor.ldm.bean.PermissionNode;
import com.studio.tensor.ldm.dao.ApiInfoMapper;
import com.studio.tensor.ldm.dao.ApiRoleMapper;
import com.studio.tensor.ldm.dao.OrderInfoMapper;
import com.studio.tensor.ldm.dao.RoleInfoMapper;
import com.studio.tensor.ldm.pojo.ApiInfo;
import com.studio.tensor.ldm.pojo.ApiRole;
import com.studio.tensor.ldm.pojo.OrderInfo;
import com.studio.tensor.ldm.pojo.RoleInfo;
import com.studio.tensor.ldm.service.PermissionInfoService;
import com.studio.tensor.ldm.utils.ByteBooleanUtils;

@Service
public class PermissionServiceImpl implements PermissionInfoService
{
	@Autowired
	ApiInfoMapper apiInfoMapper;
	@Autowired
	ApiRoleMapper apiRoleMapper;
	@Autowired
	RoleInfoMapper roleInfoMapper;
	@Autowired
	OrderInfoMapper orderInfoMapper;
	@Autowired
	UserInfoServiceImpl userInfoServiceImpl;
	
	List<PermissionNode> permissionNodeList;
	List<ApiInfo> apiList;
	
	@Override
	public Boolean insertApi(ApiInfo apiInfo)
	{
		apiInfoMapper.insertSelective(apiInfo);
		buildCache();
		return true;
	}

	@Override
	public Boolean updateApi(ApiInfo apiInfo)
	{
		apiInfoMapper.updateByPrimaryKeySelective(apiInfo);
		buildCache();
		return true;
	}

	@Override
	public Boolean deleteApi(Integer apiInfoId)
	{
		apiInfoMapper.deleteByPrimaryKey(apiInfoId);
		buildCache();
		return true;
	}

	@Override
	public List<ApiInfo> selectAllApi()
	{
		return apiInfoMapper.selectAll();
	}

	@Override
	public Boolean insertRole(RoleInfo roleInfo)
	{
		roleInfoMapper.insertSelective(roleInfo);
		buildCache();
		return true;
	}

	@Override
	public Boolean updateRole(RoleInfo roleInfo)
	{
		roleInfoMapper.updateByPrimaryKeySelective(roleInfo);
		buildCache();
		return true;
	}

	@Override
	public Boolean deleteRole(Integer roleInfoId)
	{
		roleInfoMapper.deleteByPrimaryKey(roleInfoId);
		buildCache();
		return true;
	}

	@Override
	public List<RoleInfo> selectAllRole()
	{
		return roleInfoMapper.selectAll();
	}

	@Override
	public Boolean insertRoleApis(List<ApiRole> apiRoleInfos)
	{
		for(ApiRole apiRoleInfo : apiRoleInfos)
			if(apiRoleMapper.selectNumByApiAndRoleId(
					apiRoleInfo.getApiId(), apiRoleInfo.getRoleId()) == 0)
				apiRoleMapper.insert(apiRoleInfo);
		buildCache();
		return true;
	}

	@Override
	public Boolean deleteRoleApis(List<Integer> apiRoleInfoIds)
	{
		for(Integer apiRoleInfoId : apiRoleInfoIds)
			apiRoleMapper.deleteByPrimaryKey(apiRoleInfoId);
		buildCache();
		return true;
	}

	@Override
	public Boolean insertOrder(OrderInfo orderInfo)
	{
		orderInfoMapper.insertSelective(orderInfo);
		buildCache();
		return true;
	}

	@Override
	public Boolean deleteOrder(Integer orderInfoId)
	{
		orderInfoMapper.deleteByPrimaryKey(orderInfoId);
		buildCache();
		return true;
	}

	@Override
	public Boolean isExpired(Integer userId)
	{
		OrderInfo orderInfo = orderInfoMapper.selectByUserId(userId);
		if(orderInfo.getOrderEndTime().getTime() < new Date().getTime())
			return true;
		else
			return false;
	}

	@Override
	public List<OrderInfo> selectOrderInfo(Integer start, Integer size)
	{
		return orderInfoMapper.selectAll(start, size);
	}

	@Override
	public Integer selectOrderInfoNum()
	{
		return orderInfoMapper.selectNum();
	}
	
	@Override
	public List<PermissionNode> getAllNode()
	{
		return permissionNodeList;
	}
	
	@Override
	public List<ApiInfo> getApiList()
	{
		return apiList;
	}
	
	/**
	 *------------------Tools-------------------
	 */
	
	@PostConstruct
	public void onInit()
	{
		buildCache();
	}
	
	private void buildCache()
	{
		buildNodeList();
	}
	
	private void buildNodeList()
	{
		permissionNodeList = new ArrayList<>();
		List<RoleInfo> roleList = roleInfoMapper.selectAll();
		apiList = apiInfoMapper.selectAll();
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
			if(apiListIndex == null || roleListIndex == null)
				continue;
			permissionNodeList
				.get(roleListIndex)
				.allowList.add(apiList.get(apiListIndex));
		}
	}
	
	public Boolean isApiExist(String url)
	{
		for(ApiInfo apiInfo : apiList)
			if(apiInfo.getUrl().equals(url))
				return true;
		return false;
	}
	
	public Boolean isRoleAllowThisApi(String url, Integer roleId, Integer lineLength,
					Integer userId)
	{
		for(PermissionNode permissionNode : permissionNodeList)
		{
			if(permissionNode.role.getId().equals(roleId))
			{
				for(ApiInfo allowApiInfo : permissionNode.allowList)
				{
					if(allowApiInfo.getUrl().equals(url))
					{
						if(ByteBooleanUtils.byte2Boolean(allowApiInfo.getIsCalcLength())
								&& permissionNode.role.getLength() < lineLength)
							return false;
						if(ByteBooleanUtils.byte2Boolean(allowApiInfo.getIsCalcNum()))
						{
							if(permissionNode.role.getNum() <= userInfoServiceImpl.getUserApiNum(userId))
								return false;
							userInfoServiceImpl.userApiNumPlus(userId);
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	public Boolean deleteApiRoleByApiId(Integer apiId)
	{
		return apiRoleMapper.deleteByApiId(apiId) != 0;
	}
	
	public Boolean deleteApiRoleByRoleId(Integer roleId)
	{
		return apiRoleMapper.deleteByRoleId(roleId) != 0;
	}
}
