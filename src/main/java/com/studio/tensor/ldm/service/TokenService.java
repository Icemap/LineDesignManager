package com.studio.tensor.ldm.service;

import com.studio.tensor.ldm.bean.ResultBean;

public interface TokenService
{
	String loginSetAndReturnToken(Integer id);
	ResultBean confirmToken(Integer id, String token);
	void refleshKeyLifeTime(Integer id);
}
