package com.studio.tensor.ldm.service;

public interface RedisService
{
	void setToken(String token, Integer userRoleId);
	void refreshToken(String token);
	String getUserRoleId(String token);
	
	void setConfirmCode(String phoneNum, String code);
	Boolean getConfirmCode(String phoneNum, String code);

	void setPayConfirmCode(Integer userId, String code);
	Boolean isPayConfirm(Integer userId, String code);
	
	Boolean isExist(String key);
}
