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
	
	@Override
	public ResultBean userLogin(String account, String password)
	{
		UserInfo userInfo = userInfoMapper.userLogin(account, HashUtils.getMD5(password));
		
		if(userInfo == null) return ResultBean.userNotExist();
		
		String token = tokenServiceImpl.loginSetAndReturnToken(userInfo.getId());
		tokenServiceImpl.refleshKeyLifeTime(userInfo.getId());
		
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
	public ResultBean userUpdate(UserInfo userInfo, String token)
	{
		ResultBean resultBean = tokenServiceImpl.confirmToken(userInfo.getId(), token);
		if(resultBean.getCode() == 200)
			resultBean.setResultBean(userInfoMapper.updateByPrimaryKeySelective(userInfo) == 1);
		return resultBean;
	}
}
