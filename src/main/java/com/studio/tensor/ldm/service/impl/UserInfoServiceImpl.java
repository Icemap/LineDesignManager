package com.studio.tensor.ldm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studio.tensor.ldm.bean.LoginResult;
import com.studio.tensor.ldm.bean.ResultBean;
import com.studio.tensor.ldm.dao.UserInfoMapper;
import com.studio.tensor.ldm.pojo.UserInfo;
import com.studio.tensor.ldm.service.UserInfoService;
import com.studio.tensor.ldm.utils.HashUtils;

@Service
public class UserInfoServiceImpl implements UserInfoService
{
	@Autowired
	TokenServiceImpl tokenServiceImpl;

	@Autowired
	UserInfoMapper userInfoMapper;
	
	@Autowired
	RoleInfoServiceImpl roleInfoServiceImpl;
	
	@Override
	public ResultBean userLogin(String account, String password, String apiKey)
	{
		UserInfo userInfo = userInfoMapper.userLogin(account, HashUtils.getMD5(password));
		if(userInfo == null) return ResultBean.userNotExist();
		if(!roleInfoServiceImpl.hasPermission(userInfo.getRoleId(), apiKey))
			return ResultBean.permissionDenied();
		
		String token = tokenServiceImpl.loginSetAndReturnToken(userInfo.getId(),
				userInfo.getRoleId());
		tokenServiceImpl.refleshKeyLifeTime(token);
		
		LoginResult loginResult = new LoginResult();
		loginResult.setUserInfo(userInfo);
		loginResult.setToken(token);
		return ResultBean.tokenKeyValid(loginResult);
	}

	@Override
	public ResultBean userRegister(String account, String password)
	{
		UserInfo userInfo = new UserInfo();
		userInfo.setAccount(account);
		userInfo.setHashPassword(password);
		
		userInfoMapper.insertSelective(userInfo);
		return ResultBean.tokenKeyValid(true);
	}

	@Override
	public ResultBean userUpdate(UserInfo userInfo, String token, String apiKey)
	{
		if(tokenServiceImpl.confirmToken(token))
			return ResultBean.tokenKeyValid(userInfoMapper.updateByPrimaryKeySelective(userInfo) == 1);
		if(!roleInfoServiceImpl.hasPermission(userInfo.getRoleId(), apiKey))
			return ResultBean.permissionDenied();
		
		return ResultBean.tokenKeyNotValid();
	}
}
