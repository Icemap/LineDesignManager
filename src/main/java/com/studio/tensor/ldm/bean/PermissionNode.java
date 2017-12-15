package com.studio.tensor.ldm.bean;

import java.util.ArrayList;
import java.util.List;

import com.studio.tensor.ldm.pojo.ApiInfo;
import com.studio.tensor.ldm.pojo.RoleInfo;

public class PermissionNode
{
	public List<ApiInfo> allowList;
	public RoleInfo role;
	
	public PermissionNode()
	{
		allowList = new ArrayList<>();
	}
}
