package com.studio.tensor.ldm.service;

import org.springframework.web.multipart.MultipartFile;

import com.studio.tensor.ldm.bean.ResultBean;

public interface UserInfoService
{
	ResultBean userLogin(String phoneNum, String password);
	ResultBean userRegister(String phoneNum, String password);
	ResultBean userUpdateNickName(Integer id, String nickName);
	ResultBean userUpdateIcon(Integer id, MultipartFile icon);
	ResultBean userForgetPasswordRequest(String phoneNum);
	ResultBean userForgetPasswordChange(String phoneNum, String confirmCode,
			String newPassword);
}
