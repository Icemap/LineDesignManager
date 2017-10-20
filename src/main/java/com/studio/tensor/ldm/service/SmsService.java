package com.studio.tensor.ldm.service;

import com.studio.tensor.ldm.bean.ResultBean;

public interface SmsService
{
	ResultBean getRegisterCode(String phoneNum);
	ResultBean compareRegisterCode(String phoneNum, String code);
}
