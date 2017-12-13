package com.studio.tensor.ldm.service;

public interface RedisService
{
	void setToken(String token, String userInfo);
	void refreshToken(String token);
	String getUserInfo(String token);
	
	void setConfirmCode(String phoneNum, String code);
	Boolean getConfirmCode(String phoneNum, String code);
	
	Boolean isExist(String key);
}
