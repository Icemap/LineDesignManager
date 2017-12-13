package com.studio.tensor.ldm.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.studio.tensor.ldm.bean.FileSetting;
import com.studio.tensor.ldm.bean.ResultBean;
import com.studio.tensor.ldm.dao.UserInfoMapper;
import com.studio.tensor.ldm.pojo.UserInfo;
import com.studio.tensor.ldm.service.UserInfoService;
import com.studio.tensor.ldm.utils.FileUtils;
import com.studio.tensor.ldm.utils.HashUtils;

@Service
public class UserInfoServiceImpl implements UserInfoService
{
	@Autowired
	UserInfoMapper userInfoMapper;

	@Autowired
	FileSetting fileSetting;
	
	@Override
	public ResultBean userLogin(String phoneNum, String password)
	{
		UserInfo userInfo = userInfoMapper.userLogin(
				phoneNum, HashUtils.getMD5(password));
		return ResultBean.tokenKeyValid(userInfo);
	}

	@Override
	public ResultBean userRegister(String phoneNum, String password)
	{
		UserInfo userInfo = new UserInfo();
		userInfo.setPhoneNumber(phoneNum);
		userInfo.setPassword(HashUtils.getMD5(password));
		return ResultBean.tokenKeyValid(
				userInfoMapper.insertSelective(userInfo) == 1);
	}

	@Override
	public ResultBean userUpdateNickName(Integer id, String nickName)
	{
		UserInfo userInfo = new UserInfo();
		userInfo.setId(id);
		userInfo.setNickName(nickName);
		return ResultBean.tokenKeyValid(userInfoMapper.
				updateByPrimaryKeySelective(userInfo));
	}

	@Override
	public ResultBean userUpdateIcon(Integer id, MultipartFile icon)
	{
		String fileName = FileUtils.saveFile(icon, fileSetting.getGetFilePath());
		UserInfo userInfo = new UserInfo();
		userInfo.setId(id);
		userInfo.setIconUrl(fileSetting.getSaveFilePath() + fileName);
		return ResultBean.tokenKeyValid(userInfoMapper.
				updateByPrimaryKeySelective(userInfo));
	}

	@Override
	public ResultBean userForgetPasswordRequest(String phoneNum)
	{
		return null;
	}

	@Override
	public ResultBean userForgetPasswordChange(String confirmCode, String newPassword)
	{
		return null;
	}

}
