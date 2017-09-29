package com.studio.tensor.ldm.service.impl;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Service;

import com.studio.tensor.ldm.bean.ResultBean;
import com.studio.tensor.ldm.service.TokenService;
import com.studio.tensor.ldm.utils.HashUtils;

import redis.clients.jedis.Jedis;

@Service
public class TokenServiceImpl implements TokenService
{
	Jedis jedis;
	String TOKEN_HEADER;
	Integer TOKEN_LIFE_TIME;
	
	@PostConstruct
	private void onInit()
	{
        jedis = new Jedis("wangqizhi.top");
        TOKEN_HEADER = "TOKEN-";
        TOKEN_LIFE_TIME = 1800;
	}
	
	@PreDestroy
	private void onDestory()
	{
		jedis.close();
	}
	
	@Override
	public String loginSetAndReturnToken(Integer id)
	{
		String tokenRaw = new Date().getTime() + "" + Math.random();
		String token = HashUtils.getMD5(tokenRaw);
		jedis.set(TOKEN_HEADER + id, token);
		return token;
	}

	@Override
	public ResultBean confirmToken(Integer id, String token)
	{
		String d_token = jedis.get(TOKEN_HEADER + id);
		if(d_token == null || d_token.isEmpty())
			return ResultBean.tokenKeyNotExist();
		else if(d_token.equals(token))
			return ResultBean.tokenKeyNotValid();
		return ResultBean.tokenKeyValidNotSetResult();
	}

	@Override
	public void refleshKeyLifeTime(Integer id)
	{
		jedis.expire(TOKEN_HEADER + id, TOKEN_LIFE_TIME);
	}
}
