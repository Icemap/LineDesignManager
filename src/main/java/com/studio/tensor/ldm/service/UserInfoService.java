package com.studio.tensor.ldm.service;

import com.studio.tensor.ldm.bean.ResultBean;
import com.studio.tensor.ldm.pojo.UserInfo;

public interface UserInfoService
{
	ResultBean userLogin(String account, String password);
	ResultBean userRegister(String account, String password);
	ResultBean userUpdate(UserInfo userInfo, String token);
}
