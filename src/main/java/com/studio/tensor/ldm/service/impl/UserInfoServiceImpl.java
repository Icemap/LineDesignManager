package com.studio.tensor.ldm.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.studio.tensor.ldm.bean.FileSetting;
import com.studio.tensor.ldm.bean.LoginResult;
import com.studio.tensor.ldm.bean.ResultBean;
import com.studio.tensor.ldm.dao.OrderInfoMapper;
import com.studio.tensor.ldm.dao.RoleInfoMapper;
import com.studio.tensor.ldm.dao.UserInfoMapper;
import com.studio.tensor.ldm.dao.UserStatusMapper;
import com.studio.tensor.ldm.pojo.OrderInfo;
import com.studio.tensor.ldm.pojo.RoleInfo;
import com.studio.tensor.ldm.pojo.UserInfo;
import com.studio.tensor.ldm.pojo.UserStatus;
import com.studio.tensor.ldm.service.UserInfoService;
import com.studio.tensor.ldm.utils.FileUtils;
import com.studio.tensor.ldm.utils.HashUtils;

@Service
public class UserInfoServiceImpl implements UserInfoService
{
	@Autowired
	UserInfoMapper userInfoMapper;

	@Autowired
	OrderInfoMapper orderInfoMapper;
	
	@Autowired
	FileSetting fileSetting;
	
	@Autowired
	RedisServiceImpl redisServiceImpl;
	
	@Autowired
	SmsServiceImpl smsServiceImpl;
	
	@Autowired
	UserStatusMapper userStatusMapper;
	
	@Autowired
	RoleInfoMapper roleInfoMapper;
	
	@Override
	public ResultBean userLogin(String phoneNum, String password)
	{
		UserInfo userInfo = userInfoMapper.userLogin(
				phoneNum, HashUtils.getMD5(password));
		RoleInfo roleinfo = roleInfoMapper.selectDefaultRole();

		if(userInfo == null)
			return ResultBean.tokenKeyNotValid();
		
		if(!userInfo.getRoleId().equals(roleinfo.getId()) && isExpired(userInfo.getId()))
		{
			userInfo.setRoleId(roleinfo.getId());
			userInfoMapper.updateByPrimaryKeySelective(userInfo);
		}
		
		String token = HashUtils.getMD5(phoneNum + new Date().toString());
		if(userInfo.getRoleId() == null)
			redisServiceImpl.setToken(token, "," + userInfo.getId());
		else
			redisServiceImpl.setToken(token, userInfo.getRoleId() + "," + userInfo.getId());
		LoginResult loginResult = new LoginResult();
		loginResult.setToken(token);
		loginResult.setUserInfo(userInfo);
		
		return ResultBean.tokenKeyValid(loginResult);
	}
	
	public ResultBean userLoginBackground(String phoneNum, String password)
	{
		UserInfo userInfo = userInfoMapper.userLogin(
				phoneNum, HashUtils.getMD5(password));
		String token = HashUtils.getMD5(phoneNum + new Date().toString());
		if(userInfo == null)
			return ResultBean.tokenKeyNotValid();
		else if(userInfo.getRoleId() == null)
			redisServiceImpl.setToken(token, "," + userInfo.getId());
		else
			redisServiceImpl.setToken(token, userInfo.getRoleId() + "," + userInfo.getId());
		LoginResult loginResult = new LoginResult();
		loginResult.setToken(token);
		loginResult.setUserInfo(userInfo);
		
		return ResultBean.tokenKeyValid(loginResult);
	}
	
	@Override
	public ResultBean userRegisterSendCode(String phoneNum)
	{
		if(userInfoMapper.userPhoneCount(phoneNum) != 0)
			return ResultBean.phoneNumExist();
		
		return ResultBean.tokenKeyValid(
				smsServiceImpl.getRegisterCode(phoneNum));
	}
	
	@Override
	public ResultBean userRegister(String phoneNum, String password, String code)
	{
		if(!smsServiceImpl.compareRegisterCode(phoneNum, code))
			return ResultBean.tokenKeyNotValid();
		
		UserInfo userInfo = new UserInfo();
		userInfo.setPhoneNumber(phoneNum);
		userInfo.setPassword(HashUtils.getMD5(password));
		userInfo.setRoleId(0);
		userInfo.setApiNum(0);
		
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
		return smsServiceImpl.getRegisterCode(phoneNum);
	}

	@Override
	public ResultBean userForgetPasswordChange(String phoneNum, 
			String confirmCode, String newPassword)
	{
		Boolean isCompare = smsServiceImpl.compareRegisterCode(phoneNum, confirmCode);
		if(!isCompare) return ResultBean.tokenKeyNotValid();
		
		return ResultBean.tokenKeyValid(userInfoMapper.updatePasswordByPhone(phoneNum, newPassword) == 1);
	}

	@Override
	public Boolean userRoleIdUpdate(Integer userId, Integer roleId)
	{
		UserInfo userInfo = new UserInfo();
		userInfo.setId(userId);
		userInfo.setRoleId(roleId);
		return userInfoMapper.updateByPrimaryKeySelective(userInfo) == 1;
	}

	@Override
	public List<UserInfo> getAllUser(Integer start, Integer size)
	{
		return userInfoMapper.getAllUser(start, size);
	}

	@Override
	public Integer getUserNumber()
	{
		return userInfoMapper.getUserNumber();
	}

	@Override
	public Boolean userDelete(Integer userId)
	{
		return userInfoMapper.deleteByPrimaryKey(userId) == 1;
	}

	@Override
	public Boolean userInsert(String phoneNum, String password, Integer roleId)
	{
		if(userInfoMapper.userPhoneCount(phoneNum) != 0)
			return false;
		
		UserInfo userInfo = new UserInfo();
		userInfo.setPhoneNumber(phoneNum);
		userInfo.setPassword(HashUtils.getMD5(password));
		userInfo.setRoleId(roleId);
		return userInfoMapper.insertSelective(userInfo) == 1;
	}

	@Override
	public Boolean userStatusSet(UserStatus userStatus)
	{
		Boolean result;
		if(userStatusMapper.selectUserIdNum(userStatus.getUserId()) == 1)
			result = userStatusMapper.updateByUserId(userStatus.getUserId(), userStatus.getStatusJson()) == 1;
		else
			result = userStatusMapper.insertSelective(userStatus) == 1;
		return result;
	}

	@Override
	public UserStatus userStatusGet(Integer userId)
	{
		return userStatusMapper.selectUserStatus(userId);
	}

	@Override
	public Boolean userApiNumPlus(Integer userId)
	{
		UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userId);
		userInfo.setApiNum((userInfo.getApiNum() == null ? 
				0 : userInfo.getApiNum()) + 1);
		return userInfoMapper.updateByPrimaryKeySelective(userInfo) == 1;
	}
	
	public Integer getUserApiNum(Integer userId)
	{
		UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userId);
		return userInfo.getApiNum() == null ? 0 : userInfo.getApiNum();
	}
	
	public Boolean isExpired(Integer userId)
	{
		OrderInfo orderInfo = orderInfoMapper.selectByUserId(userId);
		if(orderInfo == null)
			return true;
		
		if(orderInfo.getOrderEndTime().getTime() < new Date().getTime())
			return true;
		else
			return false;
	}
}
