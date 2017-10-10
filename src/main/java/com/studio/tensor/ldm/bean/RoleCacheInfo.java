package com.studio.tensor.ldm.bean;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.studio.tensor.ldm.pojo.RoleInfo;

public class RoleCacheInfo
{
	private Integer id;

    private String name;

    private String des;

    private List<String> apiList;

    public RoleCacheInfo(RoleInfo roleInfo)
    {
    	this.id = roleInfo.getId();
    	this.name = roleInfo.getName();
    	this.des = roleInfo.getDes();
    	this.apiList = new Gson().fromJson(roleInfo.getApiJson(), 
    			new TypeToken<List<String>>(){}.getType()); 
    }
    
    public RoleCacheInfo()
    {
    	
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des == null ? null : des.trim();
    }

	public List<String> getApiList()
	{
		return apiList;
	}

	public void setApiList(List<String> apiList)
	{
		this.apiList = apiList;
	}

}
