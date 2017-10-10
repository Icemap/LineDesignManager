package com.studio.tensor.ldm.service;

public interface TokenService
{
	String loginSetAndReturnToken(Integer id, Integer roleId);
	Boolean confirmToken(String token);
	Integer confirmTokenAndReturnRoleId(String token);
	void refleshKeyLifeTime(String token);
}
