package com.studio.tensor.ldm.bean;

import com.studio.tensor.ldm.utils.HashUtils;

public class AdminSetting
{
	private String adminAccount;
	private String adminPassword;
	
	public Boolean isAdmin(String account, String password)
	{
		if(getAdminAccount().equals(HashUtils.getMD5(account))
				&& getAdminPassword().equals(HashUtils.getMD5(password)))
			return true;
		else
			return false;
	}
	
	public String getAdminAccount()
	{
		return adminAccount;
	}
	public void setAdminAccount(String adminAccount)
	{
		this.adminAccount = adminAccount;
	}
	public String getAdminPassword()
	{
		return adminPassword;
	}
	public void setAdminPassword(String adminPassword)
	{
		this.adminPassword = adminPassword;
	}
}
