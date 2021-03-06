package com.studio.tensor.ldm.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.studio.tensor.ldm.bean.ResultBean;
import com.studio.tensor.ldm.bean.RoleAndOrder;
import com.studio.tensor.ldm.pojo.UserInfo;
import com.studio.tensor.ldm.pojo.UserStatus;

public interface UserInfoService
{
	ResultBean userLogin(String phoneNum, String password);
	ResultBean userRegisterSendCode(String phoneNum);
	ResultBean userRegister(String phoneNum, String password, String code,
			String companyName, String realName);
	
	ResultBean userUpdateNickName(Integer id, String nickName);
	ResultBean userUpdateIcon(Integer id, MultipartFile icon);
	ResultBean userForgetPasswordRequest(String phoneNum);
	ResultBean userForgetPasswordChange(String phoneNum, String confirmCode,
			String newPassword);
	ResultBean userUpdateCompanyAndRealName(Integer id, String companyName,
			String realName);
	
	Boolean userRoleIdUpdate(Integer userId, Integer roleId);
	List<UserInfo> getAllUser(Integer start, Integer size);
	Integer getUserNumber();
	Boolean userDelete(Integer userId);
	Integer userInsert(String phoneNum, String password, Integer roleId);
	
	Boolean userStatusSet(UserStatus userStatus);
	UserStatus userStatusGet(Integer userId);
	
	List<UserInfo> getByUserIds(List<Integer> userIds);
	Boolean userApiNumPlus(Integer UserId);
	
	RoleAndOrder getRoleAndOrder(String token);
}
