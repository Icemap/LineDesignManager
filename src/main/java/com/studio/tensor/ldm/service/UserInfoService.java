package com.studio.tensor.ldm.service;

public interface UserInfoService
{
	Object userLogin(String account, String password);
	Object userRegister(String account, String password);
}
